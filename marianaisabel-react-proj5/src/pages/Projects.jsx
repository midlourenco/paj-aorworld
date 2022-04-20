import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams
} from "react-router-dom";

const Projects = ()=>{

    let { id } = useParams();
    return (
        <div>
            {id && id!=""
            ?    <h2>Projecto: {id}</h2>
            : null
            }
        
        <div    style={{
            height:'100vh',
            width: '100%',
            background: 'yellow',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            fontSize: '1.5rem',
            fontWeight:'bold',
    
        }}
        >
            Projects
        </div>
        </div>
    )
    
    }
    export default Projects;