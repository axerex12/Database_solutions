# SpringProject API

Simple Spring Boot REST API for categories, products, suppliers, and orders.

## What the API can do

- Create a category (with products): `POST /categories`
- List categories: `GET /categories`
- Get category by id: `GET /categories/{id}`
- Update a category name: `PUT /categories/{id}`
- Delete a category: `DELETE /categories/{id}`
- Get products in a category: `GET /categories/{id}/products`
- Get supplier by email: `GET /suppliers/{email}`
- List suppliers: `GET /suppliers`
- Get supplier by id: `GET /suppliers/id/{id}`
- Get product by id: `GET /products/{id}`
- Get products by category: `GET /products/category/{categoryId}`
- List products: `GET /products`
- Get products by supplier: `GET /products/supplier/{supplierId}`
- Search products by name: `GET /products/search?name={name}`
- Create product: `POST /products`
- Update product fields: `PATCH /products/{id}`
- Delete product: `DELETE /products/{id}`
- Get orders by customer id: `GET /orders/customer/{customerId}`

Example requests are in `requests.http`.
