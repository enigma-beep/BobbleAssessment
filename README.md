# BobbleAssessment
Chat Application with an option to display text entered by the user like a threaded conversation 
# Android Assignment

Question 1:
 objectives:
  1. To create an app with an option to display text entered by the user like a threaded conversation.
  2. Create big text in it by holding the send button.
  3. retain the data in the conversation.
 
 Objective achieved:
 I was able to achieve all the requirements in the app. I developed a chat application using firebase realtime database to retain the conversation.
 The user has to first do a basic registration with username and password and then log into it to view the available users to chat whit.
 Then after choosing a user to  have a conversation with, we can chat like we do on basic messegaing apps like whatsapp(latest text at the bottom)
 if we type a text and hold the send button, the size of the text increases and then it is send in the chat bubble.
 if we close the app and return to it, we can still find the data intact as it is stored in the realtime database and also access it from another device aswell by using the required credentials.
 
 The ScreenShots are attached in the PDF above.
 
Question 2:
  one of the developer is facing the following error while using the application,
  Exception in thread "main" java.util.ConcurrentModificationException
    at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:859)
    at java.util.ArrayList$Itr.next(ArrayList.java:831)
    at main.java.Bobble.main(Test.java:25)
  Can you tell what he might be doing wrong? and how he can fix it? demonstrate how to
  reproduce the error, and then the resolution

java.util.ConcurrentModificationException occurs with the java collection class. the collection changes while some thread is traversing over it using the iterator iterator.next() which throws the ConcurrentModificationException.

More details in the ATTACHED PDF.
  
