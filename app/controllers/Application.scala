package controllers

import org.joda.time.LocalDate
import play.api._
import play.api.mvc._
import fincalc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def schedule = Action {
    val ps = findSchedule(Money(30000), 0.1, LocalDate.now(), Payments.equal(24))
    Ok(views.html.schedule(ps.get))
  }
}