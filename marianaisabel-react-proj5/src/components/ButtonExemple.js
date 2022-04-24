import {useNavigate} from 'react-router-dom'
//useHistory replace by useNavigate : history.push ->navigate("/home");

function ButtonExemple({path, description, ...props}){
    let navigate = useNavigate();
    function handleClick() {
      navigate(path);
    }
    
    return(
        <button onClick={handleClick}> {description} </button>


    );



};

export default ButtonExemple;