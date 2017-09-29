/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.common.message

import java.net.HttpCookie

import com.stratio.tikitakka.common.model.ContainerId
import com.stratio.tikitakka.common.model.CreateApp

sealed trait UpAndDownMessage

case class UpServiceRequest(buildApp: CreateApp, ssoToken: Option[HttpCookie]) extends UpAndDownMessage
case class UpServiceResponse(appInfo: ContainerId) extends UpAndDownMessage
case class UpServiceFails(appInfo: ContainerId, msg: String) extends UpAndDownMessage

case class DownServiceRequest(appInfo: ContainerId) extends UpAndDownMessage
case class DownServiceResponse(appInfo: ContainerId) extends UpAndDownMessage
case class DownServiceFails(appInfo: ContainerId, msg: String) extends UpAndDownMessage
