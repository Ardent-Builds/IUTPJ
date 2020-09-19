# IUTPJ

## Introducing IUT Programming Judge

It is a complete programming judging tool for hosting intra and inter university programming contests in IUT with additional functionalities of solving problems offline on various topics of programming.

# Description

It is a client-server application with 3 modules: IUTPJ_Server, IUTPJ_Admin, IUTPJ_User

-> IUTPJ_Admin module acts as an judge, where an admin can add or modify a problem or a contest

-> IUTPJ_User module gives the user's to previlege to participate in a contest or practice offline

-> IUTPJ_Server module connects everything to a network also judges the submitted codes

## System Requirements

-> Java Virtual machine with Java runtime enviroment

-> Oracle Database with privileged database user IUTPJ identified by IutPj

-> GNU GCC, G++ and JAVA JDK system path

## Installation / Building from source code

No installation needed. You can directly run individual jar files.

To build use netbeans 8.2 IDE.

Run the server first then the module you are working with(user or admin)

## Documentation

https://github.com/Ardent-Builds/IUTPJ/tree/master/Documentation


## Usage

Admin panel can 

      -> Create problems
      
      -> Modify problems
      
      -> Host contests
      
      -> See submissions(codes)
      
      -> See rank list(contest/offline solves)
      
User panel can

      -> Solve problems
      
      -> Participate in a contess
      
      -> See submissions
      
      -> See my submissions
      
      -> See ranklist
      
GUI based server: 

      -> One click to start and stop
      
      -> Create port of your own choice
      
      -> Can handle multiple instances of modules



