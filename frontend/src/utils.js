export const valid = (name, email, password, confirmPassword, description, phoneNumber)=>{
    const err={}

    if(!name) {
        err.name = "Please add your full name."
    }else if(name.length>25){
        err.name = "Full name is up to 25characters long."
    }

    if(!email){
        err.email = "Please add your email."
    }else if(!validateEmail(email)){
        err.email = "Email format is incorrect."
    }

    if(!password){
        err.password = "Please add your password."
    }else if(password.length<6){
        err.password = "Password must be at least 6 characters."
    }

    if(description.length>200){
        err.description="Description is up to 200characters long."
    }

    if(phoneNumber && phoneNumber.length>15){
        err.phoneNumber="PhoneNumber is up to 15characters long."
    }
    
    if(password !== confirmPassword){
        err.confirmPassword = "Confirm password did not match."
    }
    console.log(err)

    return{
        err,
        errLength:Object.keys(err).length
    }

}

const validateEmail = (email)=>{
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}


export const profileUpload = "/Users/yunhalee/Documents/SPRING/withEmployee/profileImages/"


export const editValid = (name, email, password, confirmPassword, description, phoneNumber)=>{
    const err={}

    if(!name) {
        err.name = "Please add your full name."
    }else if(name.length>25){
        err.name = "Full name is up to 25characters long."
    }

    if(!email){
        err.email = "Please add your email."
    }else if(!validateEmail(email)){
        err.email = "Email format is incorrect."
    }

    if(password && password.length<6){
        err.password = "Password must be at least 6 characters."
    }

    if(description.length>200){
        err.description="Description is up to 200characters long."
    }

    if(phoneNumber && phoneNumber.length>15){
        err.phoneNumber="PhoneNumber is up to 15characters long."
    }
    
    if(password !== confirmPassword){
        err.confirmPassword = "Confirm password did not match."
    }

    return{
        err,
        errLength:Object.keys(err).length
    }
}