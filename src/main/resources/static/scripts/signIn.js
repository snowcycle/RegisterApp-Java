function validateForm(){

    var id = document.getElementById('employeeId')      //sets the variable to the employeeId
    var pwd = document.getElementById('password')       //sets the variable to the password

    if(id != null && pwd != null )  //makes sure that the employeeId and password are not blank 
    {
        if(isNaN(Number(id.value)))    //checks to see if the emplyeeId is a number
        {
            if(isNaN(Number(id.value) > 0))     //checks to see if the id value is greater than 0
            {
                if(pwd.value.trim() !=='')      //checks to see if the pwd value doesn't equal a blank after triming the whitespaces
                {
                    return true;
                }else{
                    alert("Please fill out a valid Password.");  //alerts the user if the password they've typed in doesn't meet the requirements.
                    return false;
                }
            } 
            else{
                alert("Please fill out a valid EmployeeId.");   //alerts the user if the EmployeeId they've typed in doesn't meet the requirements.
                return false;
            }
        }else{
            alert("Please fill out a valid EmployeeId.");   //alerts the user if the EmployeeId they've typed in doesn't meet the requirements.
            return false;
        }
    }else{
        alert("Please fill out a valid EmployeeId and Password."); //alerts the user if the password & employeeId they've typed in doesn't meet the requirements.
        return false;
    }
}