import React from 'react'
import { useState, useEffect, } from "react";
//Redirect replace by Naviagate
import {Navigate, useParams, useNavigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";

import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    Box,
    Link,
    Select,
    Avatar,
    Badge,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    FormLabel,
    Text,
    Spinner,
    Spacer,
    Textarea,
    Tooltip,
    Editable,
    EditableInput,
    EditableTextarea,
    EditablePreview,
    ButtonGroup,
    IconButton,
    useEditableControls,
    InputRightElement
} from "@chakra-ui/react";
import { CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import RedirectButton from "../components/RedirectButton"
import ProjectViewMode from "../components/sections/Project/ProjectViewMode.js"
import ProjectEditMode from "../components/sections/Project/ProjectEditMode.js"
import EditableControls from "../components/EditableControls"

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { RiNewspaperLine} from "react-icons/ri";
import { BiEraser} from "react-icons/bi";
  //TODO: 
  function setAppError(error){
    console.log(error)
}

const defaultProject = {
    id:'',
    image: '',
    title: '',
    description: '',
    keywords: [],
    associatedUsers: [],
    associatedNews:[], 
    createdBy: [],
    createdDate: '1000-01-01',
    lastModifBy: [],
    lastModifDate: '',
    visibility: false,
    deleted: false
}


  //**********************************************MAIN FUNCTION !!!!!*************************************************************************** */

function Project( {userPriv,errorTopBar="",userId,...props}){
    const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    const navigate = useNavigate();
    //const [currentId, setCurrentId]=useState("");
    const { id } = useParams();
    console.log(id)
   // setCurrentId(id)

    /**** *******************************************STATE******************************************************** */

    const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
    const [currentProject, setCurrentProject]=useState(null);
    //modo ediçao / visualizaçao
    const [editMode, setEditClick] = useState(false);
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    



    /**** ******************************************OTHER BUTTONS********************************************************* */
   
    //função que chamamos ao clicar em "edit"/"hide" edit mode - altera o state de false para true e vice-versa
    const handleEditClick = () => setEditClick(!editMode);

    //função que chamamos ao clicar em cancelar 
    const handleCancelClick = () => setEditClick(!editMode);
    //função que chamamos ao clicar em eliminar 
    const handleDeleteClick= async()=>  {
        setEditClick(!editMode);
        const deletedUser = await del('users/'+currentProject.id)
        if (response.ok) {
            console.log("user eliminado/bloqueado com sucesso ", deletedUser);
            setAppError("");
            navigate("/about");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_login_401');
            setRestResponse("NOK");
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
                setAppError(  error );
                setRestResponse("NOK");
            }else{
                setAppError(  "error_fetch_generic" )
                setRestResponse("NOK");
            }
        }
    }


    /**** ******************************************USE EFFECT********************************************************* */
 
    // let currentProject=getUserToShow(id);
    // console.log(currentProject)
    
 useEffect(async()=>{
        console.log("dentro do userEffect");
        setRestResponse("");

        const getprojects = await get("projects/"+id)
        if (response.ok) {
            console.log(getprojects)
            setCurrentProject(getprojects);
            if(userPriv =="ADMIN"){
                setAdminPriv(true);
            }
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_login_401');
            setRestResponse("NOK");
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
                setAppError(  error );
                setRestResponse("NOK");
            }else{
                setAppError(  "error_fetch_generic" );
                setRestResponse("NOK");
            }
        }
        
        
    },[])
    /**
     * use Effect à escuta de variaveis para fazer trigger do render
     */
    useEffect(()=>{
        console.log("dentro do segundo  userEffect");
        console.log(editMode);
        setRestResponse("");
    
    },[currentProject,editMode])
    
    /**
     * use effect À escuta da variável que obriga ao scroll down
     */
    // useEffect(() => {
    //     window.scrollTo(0,document.body.scrollHeight);
    //   },[])
       



/**** ******************************************RENDER / RETURN PRINCIPAL ********************************************************* */
 

    return (
    <Box>
        <Flex 
        width="100wh"
        minHeight="20vh"
        backgroundColor="teal.400"
        justifyContent={"center"}
        ><Heading color ={"white"} ><FormattedMessage id={"project_details"} /></Heading></Flex>
           
        <Flex
        flexDirection="column"
        width="100wh"
        minHeight="65vh"
        backgroundColor="gray.200"
        justifyContent="start"
        alignItems="center"
        >

        {/* { editMode==false?
        (<Box>
        <Avatar size='2xl' bg="teal.500" mt={"-50%"} mb={10} name={currentProject.firstName + " " + currentProject.lastName} src={currentProject.image} />
        </Box>
        ):null} */}

        {error && 'Error!'}
        {loading && 'Loading...'}

        {!currentProject?
            <Spinner />
        :error?
            <WarningTwoIcon />
    
        :(editMode==false?

            <ProjectViewMode  
            isAdmin={isAdmin}
            userId={userId}
            currentProject= {currentProject} 
            editMode={editMode} 
            handleEditClick={handleEditClick} 
            handleDeleteClick={handleDeleteClick}
            handleCancelClick={handleCancelClick}
            />

            : <ProjectEditMode 
            isAdmin={isAdmin} 
            currentProject= {currentProject}  
            editMode={editMode}
            handleEditClick={handleEditClick}
            handleDeleteClick={handleDeleteClick}
            handleCancelClick={handleCancelClick} 
          
      
            />
            )
        }
        {restResponse=="OK"?
        <Text my={5} color="green"><FormattedMessage id={"sucess_on_updating"} /> </Text>
        : restResponse=="NOK"?
        <Text my={5} color="red"> <FormattedMessage id={"error_on_updating"} />  </Text>
        :null
        }
        <Box mb={30}>
            <RedirectButton path="/projects" description={intl.formatMessage({id: "_back_to_projects" })} />
        </Box >
        {/* <Avatar name={register.firstName & " " & register.lastName} src={register.image} /> */}
        </Flex>
    </Box>
    );
}

const mapStateToProps = state => {
    return { userPriv: state.loginOK.userPriv,
            userId:state.loginOK.userId,
            errorTopBar: state.errorMsg.errorTopBar
    };
  };
export default connect(mapStateToProps,{})( Project);