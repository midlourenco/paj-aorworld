import React, { useEffect } from "react"
//Redirect replace by Naviagate
import {Navigate, useNavigate} from 'react-router-dom'
function Logout() {
     // console.log(props);
   // props.history
   //exemplo para redireccionar para uma página dada uma dada condição:
    // if(true){
    //     return <Navigate to="/news" />
    // }
    const navigate = useNavigate();

    useEffect(() => {
      sessionStorage.clear();
      localStorage.clear();
      navigate("/")
    }, [])
    
// <Navigate to="/" />
  return (
  
    <>
    </>
    
  )
}

export default Logout