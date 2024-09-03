/*
 * Copyright 2011-2023 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gatling.core.feeder

import java.nio.channels.{ Channels, ReadableByteChannel }
import java.nio.charset.Charset

import scala.jdk.CollectionConverters._

import org.simpleflatmapper.lightningcsv.CsvParser

private[gatling] object SeparatedValuesParser {
  val DefaultQuoteChar: Char = '"'

  val CommaSeparator: Char = ','
  val SemicolonSeparator: Char = ';'
  val TabulationSeparator: Char = '\t'

  private def withRecordsIterator[T](columnSeparator: Char, quoteChar: Char, charset: Charset)(
      f: (Array[String], Iterator[Array[String]]) => T
  ): ReadableByteChannel => T = {
    val parser = CsvParser
      .separator(columnSeparator)
      .quote(quoteChar)

    channel => {
      val reader = Channels.newReader(new Utf8BomSkipReadableByteChannel(channel), charset.newDecoder, -1)
      val it = parser.iterator(reader)

      require(it.hasNext, "Feeder source is empty")
      val headers = it.next().map(_.trim)
      require(headers.nonEmpty, "CSV sources must have a non empty first line containing the headers")
      headers.foreach { header =>
        require(header.nonEmpty, "CSV headers can't be empty")
      }

      f(headers, it.asScala)
    }
  }

  def feederFactory(columnSeparator: Char, quoteChar: Char, charset: Charset): ReadableByteChannel => Feeder[String] =
    withRecordsIterator(columnSeparator, quoteChar, charset) { (headers, it) =>
      it.collect { case row if !(row.length == 1 && row(0).isEmpty) => ArrayBasedMap(headers, row) }
    }
}
