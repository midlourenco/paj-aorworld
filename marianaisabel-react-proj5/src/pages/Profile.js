import React from 'react'
import { useState, useEffect } from "react";
//Redirect replace by Naviagate
import {Navigate, useParams} from 'react-router-dom'
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
    Avatar,
    Badge,
    FormControl,
    FormHelperText,
    FormErrorMessage,
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
import { CheckIcon, CloseIcon, EditIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { current } from '@reduxjs/toolkit';
  //TODO: 
  function setAppError(error){
    console.log(error)
}


const ProfileViewMode=({currentUser,editMode,handleEditClick,handleCancelClick})=>{
    return (<Box>
        <Stack
    flexDir="column"
    mb="2"
    justifyContent="center"
    alignItems="center"
    >
        <Box minW={{ base: "90%", md: "468px" }} mb={20}>
            <Flex
            spacing={2}
            backgroundColor="whiteAlpha.900"
            boxShadow="md"
            minHeight={"300px"}
            p={10}
            flexDirection={"column"}
            justifyContent={"space-between"}
            >
                <Box>
                {console.log(currentUser)}                            
                    <Text fontSize='2xl' fontWeight={"bold"}> {currentUser.firstName +" "+ currentUser.lastName}</Text>
                    <Text as="i" fontSize='md'mt={1}  > {currentUser.email }</Text>
                    <br />
                    <Badge mx={2} fontSize={"10px"} color={"teal.400"} ><FormattedMessage id={currentUser.privileges} defaultMessage={"-"} /></Badge>
                </Box>
                <Box>
                    <Text fontSize='lg'> {currentUser.biography  }</Text>
                </Box>
                <Box>
                    {/* <IconButton size='sm' icon={<EditIcon />} background="whiteAlpha.900" pt={5} >Editar</IconButton> */}
                    <EditableControls editMode={editMode} handleEditClick={handleEditClick} handleCancelClick={handleCancelClick} />
                </Box>
            </Flex>
            
        </Box>
    </Stack>
    </Box>
    )
}

const ProfileEditMode=({currentUser,handleInputChange, handleCancelClick, handleSubmit, onSubmit, onError})=>{
    const intl = useIntl();
    return (<Box>
        <Stack
    flexDir="column"
    mb="2"
    justifyContent="center"
    alignItems="center"
    >
        <Box minW={{ base: "90%", md: "468px" }} mb={20}>
            <Flex
            spacing={2}
            backgroundColor="whiteAlpha.900"
            boxShadow="md"
            minHeight={"300px"}
            p={10}
            flexDirection={"column"}
            justifyContent={"space-between"}
            >
            <form onSubmit={ handleSubmit(onSubmit,onError )}>
                <FormControl>                        
                    <Input type="text" fontSize='2xl' fontWeight={"bold"}  id={"firstname"} value  = {currentUser.firstName} onChange={handleInputChange} name="firstName" />
                </FormControl>  
                <FormControl>                        
                    <Input type="text" fontSize='2xl' fontWeight={"bold"}  value  = {currentUser.lastName} onChange={handleInputChange} name="lastName" />
                </FormControl>  
                <FormControl>     
                    <Input type="email" as="i" fontSize='md'mt={1} value  = {currentUser.email }  onChange={handleInputChange} name="email"/>
                </FormControl>  
                    <br />
                    <Badge mx={2} fontSize={"10px"} color={"teal.400"} ><FormattedMessage id={currentUser.privileges} defaultMessage={"-"} /></Badge>
                
                <FormControl>
                    <Textarea fontSize='lg' value = {currentUser.biography} onChange={handleInputChange} name="biography"/> 
                </FormControl>
                <Box>
                    {/* <IconButton size='sm' icon={<EditIcon />} background="whiteAlpha.900" pt={5} >Editar</IconButton> */}
                    {/* <EditableControls editMode={editMode} handleEditClick={handleEditClick} handleCancelClick={handleCancelClick} handleOnSubmit={handleOnSubmit} /> */}
                    <SubmitButton />
                </Box>
                </form>
                <CancelButton handleCancelClick={handleCancelClick} />
            </Flex>
            
        </Box>
    </Stack>
    </Box>
    )
}


function EditableControls({editMode,handleEditClick,handleCancelClick}) {
    const intl = useIntl();
    return editMode ? (
        <ButtonGroup justifyContent='center' size='sm' >
           <CancelButton handleCancelClick={handleCancelClick} />
            <SubmitButton />
            
        </ButtonGroup>
        ) : (
        <Flex justifyContent='center'>
            <Button size='sm' rightIcon={<EditIcon />} background="whiteAlpha.900"  onClick={handleEditClick} > <FormattedMessage id={"edit"} defaultMessage={"-"}/></Button>
        </Flex>
        )
    }

function SubmitButton(){
    const intl = useIntl();
    return(
            <Tooltip label= {intl.formatMessage({id: 'update'})} aria-label='A tooltip' >
                <IconButton icon={<CheckIcon />}  mx={3}/>
            </Tooltip>
    )
}


function CancelButton({handleCancelClick} ){
    const intl = useIntl();
    return(
        <Tooltip label= {intl.formatMessage({id: 'cancel'})} aria-label='A tooltip'>
            <IconButton icon={<CloseIcon />}  onClick={handleCancelClick} mx={3} />
        </Tooltip>
    )
}


function Profile() {
    //const [currentId, setCurrentId]=useState("");
    const { id } = useParams();
    console.log(id)
   // setCurrentId(id)

    const defaultUser=  {"id": "",
        "firstName": "",
        "lastName": "",
        "email": "",
        "image": "",
        "deleted": false,
        "privileges": "",
        "biography": " "}
    const [refreshNow, setRefreshNow]=useState(false);
    const [currentUser, setCurrentUser]=useState(defaultUser);
    const {register, handleSubmit, formState: {errors}}= useForm();
    //const onSubmit = values => console.log(values); 
    //const handleSubmit(data){setData(data), console.log(data) )};

    const onSubmit = (data, e) => console.log(data, e);
    const onError = (errors, e) => console.log(errors, e);
    const intl = useIntl();
   
    //modo ediçao / visualizaçao
    const [editMode, setEditClick] = useState(false);
    //função que chamamos ao clicar em "edit"/"hide" edit mode - altera o state de false para true e vice-versa
    const handleEditClick = () => setEditClick(!editMode);

    //função que chamamos ao clicar em cancelar 
    const handleCancelClick = () => setEditClick(!editMode);
   

    const [data, setData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        biography: '',
    })

    const handleInputChange = (event) => {
        setData({
            ...data,
            [event.target.name] : event.target.value
        })
    }

    
    // let currentUser=getUserToShow(id);
    // console.log(currentUser)
    
    const { get, post, response, loading, error } = useFetch();
    useEffect(async()=>{
        console.log("dentro do userEffect");
        setEditClick(false);
        if(!id){
            const getUserProfile = await get("users/myProfile")
            if (response.ok) {
                console.log(getUserProfile)
                setCurrentUser(getUserProfile);
                setRefreshNow(true);
                setAppError("");
            } else if(response.status==401) {
                console.log("credenciais erradas? " + error)
                setAppError('error_fetch_login_401');
            }else{
                console.log("houve um erro no fetch " + error)
                if(error && error!=""){
                    setAppError(  error );
                }else{
                    setAppError(  "error_fetch_generic" );
                }
            }
        }
        
        const getUserProfile = await get("users/"+id)
        if (response.ok) {
            console.log(getUserProfile)
            setCurrentUser(getUserProfile);
            setRefreshNow(true);
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_login_401');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
                setAppError(  error );
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }
        
        
    },[])
    useEffect(()=>{
        console.log("dentro do segundo  userEffect");
        console.log(editMode);
    
    },[currentUser,editMode])
    
    
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
        {error && 'Error!'}
        {loading && 'Loading...'}
        
       
        { editMode==false?
        (<Box>
        <Avatar size='2xl' bg="teal.500" mt={"-50%"} mb={10} name={currentUser.firstName + " " + currentUser.lastName} src={currentUser.image} />
        </Box>
        ):null}
        {editMode==false?
        <ProfileViewMode  currentUser= {currentUser} editMode={editMode} handleEditClick={handleEditClick} handleCancelClick={handleCancelClick}/>
        : <ProfileEditMode currentUser= {currentUser} handleInputChange={handleInputChange} handleCancelClick={handleCancelClick} onError={onError} onSubmit={onSubmit} handleSubmit={handleSubmit} />
        }
        {/* <Avatar name={register.firstName & " " & register.lastName} src={register.imageURL} /> */}
        </Flex>
    </Box>
    );
}

export default Profile;

//INFO HARD CODED PARA TESTES
// function getUserToShow(id){
//     let currentUser;
   
//     if(!id || id=="" ){
//         return (currentUser={
//             firstName: "Emmanuel",
//             lastName: "Macron",
//             imageURL: "data:image/jpeg;base64,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",
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
//             imageURL: "https://bit.ly/dan-abramov",
//             role: "MEMBER"
//         })
//     }else if(id=="999"){
//         return ( currentUser={
//             id: "999",
//             firstName:  "Manuel",
//             lastName: "Shoarma",
//             email:  "manuelshoarma@aorprojects.pt",
//             biography:  "uieyish ashdjkho  ewhiorh whro iwo3",
//             imageURL: "https://bit.ly/sage-adebayo",
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