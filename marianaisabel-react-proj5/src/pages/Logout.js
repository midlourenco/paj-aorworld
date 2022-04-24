import React from 'react'
//Redirect replace by Naviagate
import {Navigate} from 'react-router-dom'
function Logout() {
     // console.log(props);
   // props.history
   //exemplo para redireccionar para uma página dada uma dada condição:
    // if(true){
    //     return <Navigate to="/news" />
    // }

  return (
    <Navigate to="/" />
  )
}

export default Logout