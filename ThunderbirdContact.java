/******************************************************************************
 * Copyright (C) 2019 Eric Pogue.
 * 
 * This file is licensed under the BSD-3-Clause
 * 
 * You may use any part of the file as long as you give credit in your 
 * source code.
 * 
 *****************************************************************************/

class ThunderbirdContact extends HttpRequest implements Runnable {
    private String firstName;
    public String getFirstName() {
        if(firstName.equals("")){
            String error = "User";
            return error +" "+ Integer.toString(seatLocation);
        }else{
            return Integer.toString(seatLocation) + " " + firstName;
        }
    }
    
    private String lastName;
    public String getLastName() {return lastName; }
    
    private int seatLocation; 
    public int getSeat() { return (seatLocation-1); }

    private String preferredName;
    public String getPreferredName(){ 
        if(preferredName.equals("")){
            return firstName+" "+Integer.toString(seatLocation);
        }else{
            return preferredName+" "+ Integer.toString(seatLocation);
        }
    }

    private String email;
    public String getEmail(){return email;}

    //KAI: Added the preferred name and Email field
    ThunderbirdContact(String urlIn) {
        super(urlIn);

        firstName = "";
        lastName = "";
        seatLocation = 0;
        preferredName = "";
        email = "";
        
        //KAI: added preferred name field

        // Todo: Add additional fields. 
    }

    public Boolean Load() {
        Boolean returnValue = false;
        System.out.println("Loading: " + requestURL);
        if (super.readURL()) {
            Parse(); 
            returnValue = true;
        }

        return returnValue;
    }

    public void Parse() {
        for (String s : urlContent) {
            String[] subString = s.split("\"");

            // Todo: Parse for additional fields. 
            //KAI: Added the preferred name field
            if (subString.length > 3) {
                if (subString[1].equals("firstName")) {
                    firstName = subString[3]; 
                }
                if (subString[1].equals("lastName")) {
                    lastName = subString[3];   
                }
                if (subString[1].equals("preferredName")){
                    preferredName = subString[3];
                }
                if (subString[1].equals("email")){
                    email = subString[3];
                }
                if (subString[1].equals("seatLocation")) {
                    try {
                        seatLocation = 0; 
                        if (!subString[3].equals("")) {
                            seatLocation = Integer.parseInt(subString[3]);
                        }
                    } 
                    catch (Exception e) {
                        System.out.println("Exception: " + e);
                    }
                }
            }
        }    
    }

    public void Validate() {

        // Todo: Add author's name and email address to failed messages. 
        if (firstName.length() == 0) {
            System.out.println("Validating: " + requestURL);
            System.out.println("    **Failed**: First Name (\"firstName\") required but not found\n\n");
            System.out.println(this);
        } else if (lastName.length() == 0) {   
            System.out.println("Validating: " + requestURL);
            System.out.println("    **Failed**: Last Name (\"lastName\") required but not found\n\n");
            System.out.println(this); 
        
        } else if(preferredName.length() == 0){
            System.out.println("Validating: " + requestURL);
            System.out.println("    **Failed**: Preferred Name (\"preferredName\") required but not found\n\n");
            System.out.println(this);

        } else if(email.length() == 0){
            System.out.println("Validating: " + requestURL);
            System.out.println("    **Failed**: email (\"email\") required but not found\n\n");
            System.out.println(this);

        }else{
            System.out.println("Validating: " + requestURL + "... Passed!");
        }

        if (urlContent.size() < 1) {
            System.out.println("Validating: " + requestURL);
            System.out.println("    **Failed**: No content loaded\n");
            return; // Returning from the middle of a method is controversial.

            //KAI: I simply moved from the top, to the end of the method
        }

    }

    public String toString() {
        // Todo: Add additional fields to returnString. 
        
        String returnString = "firstName: " + firstName + "\n";
        returnString = returnString + "lastName: " + lastName + "\n";
        returnString = returnString + "seatNumber: " + seatLocation + "\n";
        returnString = returnString + "preferredName: " + preferredName + "\n";
        returnString = returnString + super.toString();

        return returnString;
    }

    public void run() {
        Load();
    }
}