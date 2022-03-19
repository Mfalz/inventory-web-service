# Inventory Service

A simple CRUD web service for managing stored stuff (aka Products) in your cellar.

## Architectural decision
* The service components are organized as a relaxed three-tier architecture
* The service exposes a RESTful API interface
* Firebase is used as Identity Provider

## Supported use cases
* As a user I want to upload a list of stored products.
  * The input file is an Excel sheet, a template is available in `src/main/resources/excel_template.xlsx`.
* [WIP] As a user I want to be notified when any stored product is going to expire within the next 60, 30 and 15 days.
* [WIP] As a user I want to download a JSON encoded list of stored products.
* [WIP] As a user I want to search my stored products, including filtering.

## Installation
Build the Docker image

[WIP]

## Usage

[WIP]