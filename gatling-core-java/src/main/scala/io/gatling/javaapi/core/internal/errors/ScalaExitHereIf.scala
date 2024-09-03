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

package io.gatling.javaapi.core.internal.errors

import java.{ lang => jl }

import io.gatling.core.session.el._
import io.gatling.javaapi.core.StructureBuilder
import io.gatling.javaapi.core.error.Errors
import io.gatling.javaapi.core.internal.Expressions._
import io.gatling.javaapi.core.internal.JavaExpression

object ScalaExitHereIf {
  def apply[T <: StructureBuilder[T, W], W <: io.gatling.core.structure.StructureBuilder[W]](
      context: Errors[T, W],
      condition: String
  ): T =
    context.make(_.exitHereIf(condition.el))

  def apply[T <: StructureBuilder[T, W], W <: io.gatling.core.structure.StructureBuilder[W]](
      context: Errors[T, W],
      condition: JavaExpression[jl.Boolean]
  ): T =
    context.make(_.exitHereIf(javaBooleanFunctionToExpression(condition)))
}
