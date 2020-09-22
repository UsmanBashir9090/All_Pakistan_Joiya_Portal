package com.example.membership;


public class memberData {
    public String name, email, fatherName, profession, profileDob, designation, education, address, city, phone, cnic, role, timestamp, status, status1;
     ;


    public memberData(){

    }

    public memberData(String name, String email, String phone, String fatherName, String profession, String profileDob, String designation, String education, String address, String city, String cnic, String role, String timestamp, String status, String status1) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fatherName = fatherName;
        this.profession = profession;
        this.profileDob = profileDob;
        this.designation = designation;
        this.education = education;
        this.address = address;
        this.city = city;
        this.cnic = cnic;
        this.role =role;
        this.timestamp=timestamp;
        this.status=status;
        this.status1=status1;

    }

    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public String getCNIC() {return cnic;}
    public String getFatherName() {return fatherName;}
    public String getProfession() {return profession;}
    public String getEducation() {return education;}
    public String getDesignation() {return designation;}
    public String getAddress() {return address;}
    public String getCity() {return city;}

    public String getProfileDob() {
        return profileDob;
    }

    public String getRole() {
        return role;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getStatus1() {
        return status1;
    }
}

