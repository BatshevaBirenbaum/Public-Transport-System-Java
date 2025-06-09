# Public Transport System (Java)

## Overview
The **Public Transport System** is a Java-based application designed to manage public transportation operations, including bus routes, stations, drivers, and schedules. This system allows users to manage transportation data and offers various search functionalities, such as finding bus schedules, route information, and station locations.

## Features
- **Bus Route Management**: Add, remove, and manage bus routes, stations, and schedules.
- **Driver Management**: Store and manage driver information, such as names, contact details, and ratings.
- **Station Management**: Maintain information about bus stations, including names, contact details, and associated routes.
- **Search Functionality**:
  - Search for bus schedules by station or route.
  - View all stations associated with a specific bus route.
  - Real-time updates for bus locations.
  - Search for buses arriving at a specific station at a given time.
- **Administrative Functions**: 
  - Create and modify bus trips.
  - Add or remove stations from bus routes, adjusting station order.

## Database Design
The application uses a relational database with the following main entities:

- **Travel**: Represents a bus trip with a departure time, linked to a bus, driver, and route.
- **Bus**: Information about each bus, including license plate and seating capacity.
- **Driver**: Details about the bus driver, including name, phone number, and rating.
- **Line**: A bus route, defined by a unique number, source, and destination.
- **Station**: A bus station with its name and contact details.
- **Station_Line**: A junction table that represents the many-to-many relationship between stations and lines, storing the order of stations along a route.

## Installation

### Prerequisites:
- JDK 8 or higher
- Maven for dependency management

### Steps:

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/BatshevaBirenbaum/Public-Transport-System-Java.git
   ```
2.Navigate to the project directory:
   ```bash
   cd Public-Transport-System-Java
   ```

3.Compile and package the application using Maven:
  ```bash
   mvn clean package
  ```

4.Run the application:
  ```bash
  mvn exec:java
```
## Usage
Once the application is running, you can use the various search and administrative functions to interact with the system. For example:

**Search for a bus route by station**: Find all buses arriving at a specific station.

**View all stations for a bus route**: Retrieve the complete list of stations along a specific bus line.

**Retrieve real-time updates for bus locations**: Track buses as they move along their routes.


## Contributing
Contributions are welcome! If you find a bug or would like to add new features, feel free to fork the repository, make your changes, and submit a pull request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.




