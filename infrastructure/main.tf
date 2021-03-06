provider "azurerm" {}

locals {

  preview_app_service_plan        = "${var.product}-${var.component}-${var.env}"
  non_preview_app_service_plan    = "${var.product}-${var.env}"
  app_service_plan                = "${var.env == "preview" || var.env == "spreview" ? local.preview_app_service_plan : local.non_preview_app_service_plan}"

  preview_vault_name              = "${var.raw_product}-aat"
  non_preview_vault_name          = "${var.raw_product}-${var.env}"
  key_vault_name                  = "${var.env == "preview" || var.env == "spreview" ? local.preview_vault_name : local.non_preview_vault_name}"
}

resource "azurerm_resource_group" "rg" {
  name     = "${var.product}-${var.component}-${var.env}"
  location = "${var.location}"
  tags     = "${merge(var.common_tags, map("lastUpdated", "${timestamp()}"))}"
}

data "azurerm_key_vault" "ia_key_vault" {
  name                = "${local.key_vault_name}"
  resource_group_name = "${local.key_vault_name}"
}

module "ia_case_validation_api" {
  source              = "git@github.com:hmcts/cnp-module-webapp?ref=master"
  product             = "${var.product}-${var.component}"
  location            = "${var.location}"
  env                 = "${var.env}"
  ilbIp               = "${var.ilbIp}"
  resource_group_name = "${azurerm_resource_group.rg.name}"
  subscription        = "${var.subscription}"
  capacity            = "${var.capacity}"
  instance_size       = "${var.instance_size}"
  common_tags         = "${merge(var.common_tags, map("lastUpdated", "${timestamp()}"))}"
  asp_name            = "${local.app_service_plan}"
  asp_rg              = "${local.app_service_plan}"

  app_settings = {
    LOGBACK_REQUIRE_ALERT_LEVEL = false
    LOGBACK_REQUIRE_ERROR_CODE  = false
  }
}
