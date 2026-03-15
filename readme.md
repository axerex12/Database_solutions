# SpringProject API

Simple Spring Boot REST API for categories, products, suppliers, and orders.

## What the API can do

- Create a category (with products): `POST /categories`
- Get supplier by email: `GET /suppliers/{email}`
- Get product by id: `GET /products/{id}`
- Get products by category: `GET /products/category/{categoryId}`
- Create product: `POST /products`
- Update product fields: `PATCH /products/{id}`
- Delete product: `DELETE /products/{id}`
- Get orders by customer id: `GET /orders/customer/{customerId}`

Example requests are in `requests.http`.
