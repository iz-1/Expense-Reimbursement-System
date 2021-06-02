# Expense Reimbursement System

## Project Description
The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Backend Technologies Used

* Java v8
* Mongo DB / JDBC
* Vertx
* JUnit
* Gradle

## Frontend Technologies Used

* React
* Typescript
* JavaScript
* Axios
* HTML v5
* CSS v3

## Features

* Employee can request reimbursement with itemized list
* Manager can view all requests/employees/an employee's request history
* Manager can review a request
* Manager can create new employees
* Users can update profile and password reset

## Getting Started

Commands have only been tested on Windows 10.

Clone repo from command line/gitbash/powershell:  
`git clone https://github.com/iz-1/Expense-Reimbursement-System.git`  
  
Setup database:  
https://fastdl.mongodb.org/windows/mongodb-windows-x86_64-4.4.6-signed.msi (https://www.mongodb.com/try/download/community)  
start the repo if not already started (check documentation)  
port 27017 (default)  
  
Install gradle:  
https://gradle.org/release-candidate/
  
Backend compiled with:  
JDK 1.8  

Start server:  
`gradle run`  
  
Frontend:  
`yarn`  
`yarn add typescript --dev`  
`yarn add @types/react`  
`yarn add @types/react-dom`  
`yarn add @types/react-router`  
`yarn start`  
   
## Usage

Accessing the frontend login screen.
![loginscreen](https://github.com/iz-1/Expense-Reimbursement-System/blob/main/screens/download%20(7).jpg?raw=true)

After logging as a employee, the view is transferred to home.
![homescreen](https://github.com/iz-1/Expense-Reimbursement-System/blob/main/screens/download%20(1).jpg?raw=true)

Employees can check and create new reimbursement requests.
![expense](https://github.com/iz-1/Expense-Reimbursement-System/blob/main/screens/download%20(2).jpg?raw=true)

Managers' home page after login, they are able to view all requests, all employees and an employees request history.
![mgrhome](https://github.com/iz-1/Expense-Reimbursement-System/blob/main/screens/download%20(4).jpg?raw=true)

Managers' can review pending requests or inspect reviewed requests to see the reviewer.
![review](https://github.com/iz-1/Expense-Reimbursement-System/blob/main/screens/download%20(6).jpg?raw=true)

## License

Copyright (c) 2021, iz-1
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
