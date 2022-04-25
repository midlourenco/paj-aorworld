import React from 'react'
import { useState } from "react";
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
    HStack,
    InputLeftElement,
    chakra,
    Box,
    Link,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
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

function getUserToShow(id){
    let currentUser;
    
    if(!id || id=="" ){
        return (currentUser={
            firstName: "Emmanuel",
            lastName: "Macron",
            imageURL: "data:image/jpeg;base64,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",
            biography: "presidente de França",
            email: "macron@fr.fr"
        })
    }else if(id=="1"){
        return (currentUser={
            id: "1",
            firstName:  "Carlos",
            lastName: "Pita",
            email:  "carlospita@aorprojects.pt",
            biography:  "uieyish ashdjkho  ewhiorh whro iwo3",
            imageURL: "https://bit.ly/dan-abramov",
            role: "MEMBER"
        })
    }else if(id=="2"){
        return ( currentUser={
            id: "2",
            firstName:  "Manuel",
            lastName: "Shoarma",
            email:  "manuelshoarma@aorprojects.pt",
            biography:  "uieyish ashdjkho  ewhiorh whro iwo3",
            imageURL: "https://bit.ly/sage-adebayo",
            role: "ADMIN"
        })
    }

    
}


function Profile() {
    

    const {register, handleSubmit, formState: {errors}}= useForm();
    //const onSubmit = values => console.log(values); 
    //const handleSubmit(data){setData(data), console.log(data) )};

    const onSubmit = (data, e) => console.log(data, e);
    const onError = (errors, e) => console.log(errors, e);


    const [data, setData]= useState("");
    //mostrar e esconder a password
    const [showPassword, setShowPassword] = useState(false);
    //função que chamamos ao clicar em "show"/"hide" password - altera o state de false para true e vice-versa
    const handleShowClick = () => setShowPassword(!showPassword);

    //https://stackoverflow.com/questions/39630620/react-intl-how-to-use-formattedmessage-in-input-placeholder
    const intl = useIntl();

    function EditableControls() {
        const {
          isEditing,
          getSubmitButtonProps,
          getCancelButtonProps,
          getEditButtonProps,
        } = useEditableControls()
    
        return isEditing ? (
          <ButtonGroup justifyContent='center' size='sm'>
            <IconButton icon={<CheckIcon />} {...getSubmitButtonProps()} />
            <IconButton icon={<CloseIcon />} {...getCancelButtonProps()} />
          </ButtonGroup>
        ): null;
        // ) : (
        //   <Flex justifyContent='center'>
        //     <IconButton size='xs' icon={<EditIcon />} {...getEditButtonProps()} />
        //   </Flex>
        // )
      }

   
    const { id } = useParams();
    console.log(id)
    let currentUser=getUserToShow(id);
    console.log(currentUser)
    
   


    
    return (
        <Box
            
        >
            <Flex 
            width="100wh"
            minHeight="25vh"
            backgroundColor="teal.400"
            >

            </Flex>
           <Flex
            flexDirection="column"
            width="100wh"
            minHeight="60vh"
            backgroundColor="gray.200"
            justifyContent="start"
            alignItems="center"
            >
            <Box>
            <Avatar size='2xl' bg="teal.500" mt={"-50%"} mb={10} name={currentUser.firstName + " " + currentUser.lastName} src={currentUser.imageURL} />
            </Box>
                <Stack
                flexDir="column"
                mb="2"
                justifyContent="center"
                alignItems="center"
                >
                
                <Box minW={{ base: "90%", md: "468px" }} mb={20}>
                    <Stack
                        spacing={4}
                        p="1rem"
                        backgroundColor="whiteAlpha.900"
                        boxShadow="md"
                    >
                    <Flex display={"flex"} flexDirection={"row"} justifyContent={"center"} alignSelf={"center"} >   
                        <Editable
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
                        </Editable>


            
                        
                        
                        </Stack>
                    
                    </Box>
                </Stack>



            </Flex>
        {/* <Avatar name={register.firstName & " " & register.lastName} src={register.imageURL} /> */}
    
        </Box>
    );
}

export default Profile