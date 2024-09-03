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

package io.gatling.charts.report

import java.nio.file.{ Files, Path }

import scala.util.Using

import io.gatling.core.config.GatlingConfiguration

private[charts] class TemplateWriter(path: Path) {
  def writeToFile(output: String)(implicit configuration: GatlingConfiguration): Unit =
    Using.resource(Files.newBufferedWriter(path, configuration.core.charset))(_.write(output))
}
