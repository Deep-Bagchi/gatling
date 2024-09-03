import sbt._
import sbt.Keys._
import wartremover.WartRemover.autoImport._
import wartremover.Wart

object CodeAnalysis {
  lazy val disable = Seq(
    Compile / compile / wartremoverErrors := Seq.empty,
    Test / compile / wartremoverErrors := Seq.empty
  )

  lazy val settings = Seq(
    Compile / compile / wartremoverErrors := Warts.allBut(disabledWarts: _*),
    Test / compile / wartremoverErrors := (Compile / compile / wartremoverErrors).value
  )

  private def disabledWarts: List[Wart] =
    List(
      Wart.Any,
      Wart.AutoUnboxing,
      Wart.AsInstanceOf,
      Wart.Equals,
      Wart.IterableOps,
      Wart.ImplicitConversion,
      Wart.ImplicitParameter,
      Wart.JavaSerializable,
      Wart.MutableDataStructures,
      Wart.NonUnitStatements,
      Wart.Nothing,
      Wart.Null,
      Wart.Overloading,
      Wart.OptionPartial, // Generated by boopickle macros
      Wart.Serializable, // breaks JMS's Serializable usage, looks like a bug
      Wart.StringPlusAny, // see https://github.com/wartremover/wartremover/issues/447
      Wart.Throw,
      Wart.ToString,
      Wart.TripleQuestionMark,
      Wart.Var,
      Wart.While
    )
}