# Image Processing Service

## Overview
This project is an Image Processing Service built using Java Spring Boot. It allows users to upload images, perform various transformations, and retrieve them. The service includes user authentication via JWT tokens, image management, and transformation operations such as resizing, cropping, and rotating.

## Features

### 1. User Authentication
- **Sign-Up**: Allows users to create an account.
- **Log-In**: Allows users to log into their account.
- **JWT Authentication**: Secure endpoints using JWT tokens for authenticated access.

### 2. Image Management
- **Upload Image**: Users can upload images to the service.
- **Transform Image**: Perform image transformations such as resize, rotate, and more.
- **Retrieve Image**: Retrieve saved or transformed images in their respective formats.
- **List Images**: List all uploaded images by the user with metadata.

## Endpoints

### Authentication Endpoints

1. **Register a new user**
    - `POST /register`
    - Request Body:
      ```json
      {
        "name": "name",
        "username": "user1",
        "password": "password123"
      }
      ```
    - Response: The user object with a JWT token.

2. **Log in an existing user**
    - `POST /login`
    - Request Body:
      ```json
      {
        "username": "user1",
        "password": "password123"
      }
      ```
    - Response: The user object with a JWT token.

### Image Management Endpoints

1. **Upload an image**
    - `POST /image`
    - Request Body: Multipart form-data with the image file.
    - Example cURL command:
      ```bash
      curl -X POST http://localhost:8080/image \
      -H "Authorization: Bearer <JWT Token>" \
      -F image=@/path/to/image.png
      ```

2. **Retrieve all images**
    - `GET /image`
    - Response: A list of images uploaded by the user, including metadata.
    - Example cURL command:
      ```bash
      curl -X GET http://localhost:8080/image \
      -H "Authorization: Bearer <JWT Token>"
      ```

3. **Download an image**
    - `GET /image/{id}`
    - Response: The image file.
    - Example cURL command:
      ```bash
      curl -X GET http://localhost:8080/image/{id} \
      -H "Authorization: Bearer <JWT Token>"
      ```

4. **Resize an image**
    - `GET /image/{id}/resize`
    - Request Parameters: `width`, `height`
    - Response: The resized image file.
    - Example cURL command:
      ```bash
      curl -X GET "http://localhost:8080/image/{id}/resize?width=500&height=500" \
      -H "Authorization: Bearer <JWT Token>"
      ```

5. **Rotate an image**
    - `GET /image/{id}/rotate`
    - Request Parameter: `angle`
    - Response: The rotated image file.
    - Example cURL command:
      ```bash
      curl -X GET "http://localhost:8080/image/{id}/rotate?angle=90" \
      -H "Authorization: Bearer <JWT Token>"
      ```

## Image Transformation Operations

The following transformations are supported:
- **Resize**: Resize an image by specifying the width and height.
- **Rotate**: Rotate an image by a given angle.

## Technologies Used
- **Spring Boot**
- **Spring Security with JWT**
- **Spring Data JPA**
- **Image Processing Libraries**: `Thumbnailator` or `imgscalr` for image transformations.
- **MySQL/PostgreSQL**: For storing user and image metadata.
- **Local Storage or Cloud Storage**: To store uploaded and transformed images.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/image-processing-service.git
   cd image-processing-service
