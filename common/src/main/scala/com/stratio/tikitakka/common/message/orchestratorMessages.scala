/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.common.message

import com.stratio.tikitakka.common.model.discovery.DiscoveryAppInfo
import com.stratio.tikitakka.common.state.ApplicationState

sealed trait OrchestratorMessages

case class RegisterApplication(appId: DiscoveryAppInfo) extends OrchestratorMessages

case class GetApplicationInfo(appId: String) extends OrchestratorMessages

object GetApplicationInfo {
  def apply(app: DiscoveryAppInfo) = new GetApplicationInfo(app.id)
}

case class UnregisterApplication(appId: String) extends OrchestratorMessages

object UnregisterApplication {
  def apply(app: DiscoveryAppInfo) = new UnregisterApplication(app.id)
}

case class ResponseApplicationState(appId: Option[ApplicationState]) extends OrchestratorMessages
