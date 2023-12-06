# Project Overview

This document provides an overview of the project, including documentation for key files and instructions for setting up and running the project with a mock API/database.

## File Documentation

### App Component Template (`app.component.html`)

- **Purpose**: Serves as the template for the main component of the application.
- **Structure**:
  - Heading displaying "API Data".
  - Paragraph displaying the title of the data object, if it exists.
  - Button triggering the `onButtonClick()` method in the TypeScript file.
  - Div showing detailed information about the data object (title, release date, ID).
- **Features**:
  - Angular interpolation for displaying values.
  - Event binding for button clicks.
  - `*ngIf` directive for conditional rendering.

### App Component (`app.component.ts`)

- **Purpose**: Main component of the application.
- **Imports**: Modules like `Component`, `OnInit`, `HttpClient`, and `ApiService`.
- **Component Metadata**:
  - `selector: 'app-root'`
  - `templateUrl: './app.component.html'`
  - `styleUrls: ['./app.component.css']`
- **Class Definition**: Implements `OnInit` with properties like `data` and `title`.
- **Constructor**: Defines `apiService` for dependency injection.
- **Methods**:
  - `ngOnInit()`: Lifecycle hook for initialization.
  - `onButtonClick()`: Fetches data from the API and updates `data`.

## Setup and Running Instructions

### Prerequisites

- Node.js and npm installed on your system.

### Project Setup

1. **Navigate to the Project Directory**
   ```bash
   cd <project-directory>

2. Install Project Dependencies: **npm install**
3. Install json-server Globally: **npm install -g json-server**
4. **Running the Mock API/DB**
5. Start the Mock API/DB Server: **json-server --host 127.0.0.1 --watch db.json --routes routes.json --port 5001**
6. **Starting the Application**
7. Run the Application: **npm start**

Additional Notes
- Ensure db.json contains the mock data you intend to use with the API.
- Replace <project-directory> with the actual path to your project directory.

