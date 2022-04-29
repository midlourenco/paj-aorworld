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
import ProfileViewMode from "../components/sections/Profile/ProfileViewMode"
import ProfileEditMode from "../components/sections/Profile/ProfileEditMode"
import EditableControls from "../components/EditableControls"
  //TODO: 
  function setAppError(error){
    console.log(error)
}
const defaultUser=  {"id": "",
        "firstName": "",
        "lastName": "",
        "email": "",
        "image": "",
        "deleted": false,
        "privileges": "VIEWER",
        "biography": " "}





  //**********************************************MAIN FUNCTION !!!!!*************************************************************************** */

function Profile({userPriv,errorTopBar="",...props}) {
    const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    let navigate = useNavigate();
    //const [currentId, setCurrentId]=useState("");
    const { id } = useParams();
    console.log("id no url: ", id)
   // setCurrentId(id)

    /**** *******************************************STATE******************************************************** */

    const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
    const [currentUser, setCurrentUser]=useState(defaultUser);
    //modo ediçao / visualizaçao
    const [editMode, setEditClick] = useState(false);
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    const [scrollDown, setScrollDown]=useState(false)

    const [assocProj,setAssocProj]=useState([]);
    const [assocNews,setAssocNews]=useState([]);
    const [projCreatedByMe, setProjCreatedByMe]=useState([]);


    /**** ****************************************FORM*********************************************************** */
    //fuções que chamos ao submeter o formulário de edição
    const {register, handleSubmit, formState: {errors}}= useForm();
    //const onSubmit = values => console.log(values); 
    //const handleSubmit(data){setData(data), console.log(data) )};

    //const onSubmit = (data, e) => console.log(data, e);
    async function onSubmit (data, e) {
        const updateUser = await post('users/'+currentUser.id, data)
        if (response.ok) {
            console.log("user atualizado com sucesso ", updateUser);
            setRestResponse("OK");
            setScrollDown(true);
            setAppError("");
           
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setRestResponse("NOK");
            setScrollDown(true);
            setAppError('error_fetch_login_401');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
                setRestResponse("NOK");
                setScrollDown(true);
                setAppError(  error );
            }else{
                setRestResponse("NOK");
                setScrollDown(true);
                setAppError(  "error_fetch_generic" );
            }
        }
    }
    
    const onError = (errors, e) => console.log(errors, e);

    /**** ******************************************OTHER BUTTONS********************************************************* */
   
    //função que chamamos ao clicar em "edit"/"hide" edit mode - altera o state de false para true e vice-versa
    const handleEditClick = () => setEditClick(!editMode);

    //função que chamamos ao clicar em cancelar 
    const handleCancelClick = () => setEditClick(!editMode);
    //função que chamamos ao clicar em eliminar 
    const handleDeleteClick= async()=>  {
        setEditClick(!editMode);
        const deletedUser = await del('users/'+currentUser.id)
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
 
    // let currentUser=getUserToShow(id);
    // console.log(currentUser)
    
 useEffect(async()=>{
        console.log("dentro do userEffect");
        setRestResponse("");
        if(!id || id==undefined || id==""){
            const getUserProfile = await get("users/myProfile")
            if (response.ok) {
                console.log(getUserProfile)
                setCurrentUser(getUserProfile);
                if(userPriv =="ADMIN"){
                    setAdminPriv(true);
                }
                setAppError("");
            } else if(response.status==401) {
                console.log("credenciais erradas? " + error)
                 setRestResponse("NOK");
                setAppError('error_fetch_login_401');
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
        }else{
        
        const getUserProfile = await get("users/"+id)
        if (response.ok) {
            console.log(getUserProfile)
            setCurrentUser(getUserProfile);
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
        const getAssocProj = await get("users/"+id)
        if (response.ok) {
            console.log(getAssocProj)
            setAssocProj(getAssocProj);
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
        




    }

        
    },[])
    /**
     * use Effect à escuta de variaveis para fazer trigger do render
     */
    useEffect(()=>{
        console.log("dentro do segundo  userEffect");
        console.log(editMode);
        setRestResponse("");
    
    },[currentUser,editMode])
    
    /**
     * use effect À escuta da variável que obriga ao scroll down
     */
    useEffect(() => {
        window.scrollTo(0,document.body.scrollHeight);
    },[scrollDown])




/**** ******************************************RENDER / RETURN PRINCIPAL ********************************************************* */
 

    return (
    <Box>
        <Flex 
        width="100wh"
        minHeight="20vh"
        backgroundColor="teal.400"
        ></Flex>
        <Flex
        flexDirection="column"
        width="100wh"
        minHeight="65vh"
        backgroundColor="gray.200"
        justifyContent="start"
        alignItems="center"
        >

        { editMode==false?
        (<Box>
        <Avatar size='2xl' bg="teal.500" mt={"-50%"} mb={10} name={currentUser.firstName + " " + currentUser.lastName} src={currentUser.image} />
        </Box>
        ):null}

        {error && 'Error!'}
        {loading && 'Loading...'}

        {error?
            <WarningTwoIcon />
    
        :(editMode==false?

            <ProfileViewMode  
            isAdmin={isAdmin}
            currentUser= {currentUser} 
            editMode={editMode} 
            handleEditClick={handleEditClick} 
            handleDeleteClick={handleDeleteClick}
            handleCancelClick={handleCancelClick}
            />

            : <ProfileEditMode 
            isAdmin={isAdmin} 
            currentUser= {currentUser}  
            editMode={editMode}
            handleEditClick={handleEditClick}
            handleDeleteClick={handleDeleteClick}
            handleCancelClick={handleCancelClick} 
            onError={onError} 
            onSubmit={onSubmit} 
            handleSubmit={handleSubmit} 
            register={register}  
            errors={errors}
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
            <RedirectButton path="/about" description={intl.formatMessage({id: "_back_to_team" })} />
        </Box >
        {/* <Avatar name={register.firstName & " " & register.lastName} src={register.image} /> */}
        </Flex>
    </Box>
    );
}

const mapStateToProps = state => {
    return { userPriv: state.loginOK.userPriv,
            errorTopBar: state.errorMsg.errorTopBar
    };
  };
export default connect(mapStateToProps,{})(Profile);









//INFO HARD CODED PARA TESTES
// function getUserToShow(id){
//     let currentUser;
   
//     if(!id || id=="" ){
//         return (currentUser={
//             firstName: "Emmanuel",
//             lastName: "Macron",
//             image: "data:image/jpeg;base64,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",
//             biography: "presidente de França",
//             email: "macron@fr.fr"
//         })
//     }else if(id=="9999"){
//         return (currentUser={
//             id: "9999",
//             firstName:  "Carlos",
//             lastName: "Pita",
//             email:  "carlospita@aorprojects.pt",
//             biography:  "uieyish ashdjkho  ewhiorh whro iwo3",
//             image: "https://bit.ly/dan-abramov",
//             role: "MEMBER"
//         })
//     }else if(id=="999"){
//         return ( currentUser={
//             id: "999",
//             firstName:  "Manuel",
//             lastName: "Shoarma",
//             email:  "manuelshoarma@aorprojects.pt",
//             biography:  "uieyish ashdjkho  ewhiorh whro iwo3",
//             image: "https://bit.ly/sage-adebayo",
//             role: "ADMIN"
//         })
//     }

    
// }





//ALTERNATIVA CHAKRA PARA EDITAR O TEXTO

// function EditableControls() {
//     const {
//       isEditing,
//       getSubmitButtonProps,
//       getCancelButtonProps,
//       getEditButtonProps,
//     } = useEditableControls()

//     return isEditing ? (
//       <ButtonGroup justifyContent='center' size='sm'>
//         <IconButton icon={<CheckIcon />} {...getSubmitButtonProps()} />
//         <IconButton icon={<CloseIcon />} {...getCancelButtonProps()} />
//       </ButtonGroup>
//     ): null;
//     // ) : (
//     //   <Flex justifyContent='center'>
//     //     <IconButton size='xs' icon={<EditIcon />} {...getEditButtonProps()} />
//     //   </Flex>
//     // )
//   }



{/* <Editable
                            textAlign='center'
                            defaultValue={currentUser.firstName}
                            fontSize='2xl'
                            isPreviewFocusable={true}
                            selectAllOnFocus={false}
                            >
                                <Tooltip label="Click to edit">
                                    <EditablePreview px={1} pt={1} _hover={{background: "gray.100" }}/>
                                </Tooltip>
                                <Input pl={2} pt={1} as={EditableInput} />
                                <EditableControls />
                            </Editable>

                            <Editable
                            textAlign='center'
                            defaultValue={currentUser.lastName}
                            fontSize='2xl'
                            isPreviewFocusable={true}
                            selectAllOnFocus={false}
                            >
                                <Tooltip label="Click to edit">
                                    <EditablePreview pr={1} pt={1}  _hover={{background: "gray.100" }} />
                                </Tooltip>
                                <Input as={EditableInput} />
                                <EditableControls />
                            </Editable>
                        </Flex>     
                            
                        <Editable
                        textAlign='center'
                        defaultValue= {currentUser.email }
                        fontSize='sm'
                        className='editable-rm-margin'
                        isPreviewFocusable={true}
                        selectAllOnFocus={false}
                        >
                            <Tooltip label="Click to edit">
                                <EditablePreview mt={0} px={2} pb={1} _hover={{background: "gray.100" }}/>
                            </Tooltip>
                            <Input mt={0} as={EditableInput} />
                            <EditableControls />
                        </Editable>

                        <Editable
                        textAlign='center'
                        defaultValue= {currentUser.biography }
                        fontSize='md'
                        isPreviewFocusable={true}
                        selectAllOnFocus={false}
                        >
                            <Tooltip label="Click to edit">
                                <EditablePreview pr={2} py={4} _hover={{background: "gray.100" }}/>
                            </Tooltip>
                            <Input as={EditableInput} />
                            <EditableControls />
                        </Editable> */}

    //     const [data, setData] = useState({
    //     firstName: '',
    //     lastName: '',
    //     email: '',
    //     biography: '',
    // })

    // const handleInputChange = (event) => {
    //     setData({
    //         ...data,
    //         [event.target.name] : event.target.value
    //     })
    //}