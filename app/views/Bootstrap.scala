package views

import views.html.helper.FieldConstructor

object Bootstrap {
  implicit val bootstrapFieldConstructor = FieldConstructor(views.html.bootstrap.fieldconstructor.f)
}
