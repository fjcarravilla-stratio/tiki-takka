/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.updown.marathon

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpMethods, ResponseEntity}
import akka.stream.ActorMaterializer
import com.stratio.tikitakka.common.model.marathon.MarathonApplication
import com.stratio.tikitakka.common.util.HttpRequestUtils
import play.api.libs.json.Json

import scala.concurrent.Future

trait MarathonTestsUtils extends HttpRequestUtils {
  implicit val system: ActorSystem
  implicit val actorMaterializer: ActorMaterializer
  implicit val uri: String


  def createApplication(application: MarathonApplication): Future[ResponseEntity] = {
    val resource = s"v2/apps"
    doRequest(uri, resource, HttpMethods.POST, Some(Json.toJson(application)))
  }

  def destroyApplication(applicationId: String): Future[ResponseEntity] = {
    val resource = s"v2/apps/$applicationId"
    doRequest(uri, resource, HttpMethods.DELETE)
  }
}
