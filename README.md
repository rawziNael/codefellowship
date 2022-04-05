# Lab16: codefellowship 
- Build an app that allows users to create their profile on CodeFellowship.  
- The site should have a splash page at the root route (/) that contains basic information about the site, as well as a link to the “sign up” page.  
- An ApplicationUser should have a username, password (will be hashed using BCrypt), firstName, lastName, dateOfBirth, bio, and any other fields you think are useful.  
- The site should allow users to create an ApplicationUser on the “sign up” page. (For now, there is no such thing as login.)

# Lab17: codefellowship 
Upon logging in, users can be taken to a /myprofile route that displays their information. include e picture, which is the same for every user, and their basic information from ApplicationUser.  
The site have a page which allows viewing the data about a single ApplicationUser, at a route /users/{id} which include a default profile picture, which is the same for every user, and their basic information.  
added Post entity to  application that has a body and a createdAt timestamp.  
A logged-in user can be able to create a Post, and a post belong to the user that created it.  

