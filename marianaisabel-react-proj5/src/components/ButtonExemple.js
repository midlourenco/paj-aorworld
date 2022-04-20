import {useNavigate} from 'react-router-dom'
//useHistory replace by useNavigate : history.push ->navigate("/home");

function ButtonExemple({hrf}){
    let navigate = useNavigate();
    function handleClick() {
      navigate({hrf});
    }
    
    return(
        <button onClick={handleClick}> Go Home </button>


    );



};

export default ButtonExemple;