import ButtonExemple from "../components/ButtonExemple";

const AboutUs = ()=>{
    return (
        <div style={{
            height:'100vh',
            width: '100%',
            background: 'red',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            fontSize: '1.5rem',
            fontWeight:'bold',
    
        }}
        >
           About Us
           <ButtonExemple />
        </div>
    )
    
    }
    export default AboutUs;