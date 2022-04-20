import {Navigate} from 'react-router-dom'
//Redirect replace by Naviagate

const Login = (props) =>{
    console.log(props);
   // props.history
    if(true){
        return <Navigate to="/news" />
    }
    
    return (
        <div    style={{
            height:'100vh',
            width: '100%',
            background: 'grey',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            fontSize: '1.5rem',
            fontWeight:'bold',
    
        }}
        >
            Login
        </div>
    )
    
    }
    export default Login;