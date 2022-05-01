import React from 'react'
import { useState, useEffect, } from "react";
//Redirect replace by Naviagate
import {Navigate, useParams, useNavigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";
import { useToast } from '@chakra-ui/react'
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
    Image,
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
    InputRightElement,
    Table,
    Thead,
    Tbody,
    Tfoot,
    Tr,
    Th,
    Td,
    TableCaption,
    TableContainer,
} from "@chakra-ui/react";
import { ExternalLinkIcon, CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../../EditableControls"
//TODO: 
function setAppError(error){
    console.log(error)
}



const FormUserToAssoc=({ user,projectId,removeUserAssociated,...props})=>{
    const navigate = useNavigate();
    const intl = useIntl();
    const toast = useToast()
    const {register, watch,handleSubmit, formState: {errors}}= useForm();

    const { get, post, del, response, loading, error } = useFetch();
   
    const { id } = useParams();
    console.log(id)



        /**** *******************************************STATE******************************************************** */
        const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
        const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    
    /**** ******************************************SUBMIT********************************************************* */
 
    const onSubmit = async(data, e) => {
        // if(data.projects==0)  data.projects=[];
        // if(data.users==0)  data.users=[];
        // if(data.visibility=="public")  data.visibility=true;
        // if(data.visibility=="private")  data.visibility=false;
        // {
        //     "userId" : "8",
        //     "userRoleInProject" : "{{$randomJobTitle}}"
        // }
      
        console.log(data, e,data.visibility);
        const createdNews = await post('projects/'+id+"/associateUser", data)
        if (response.ok) {
            console.log("noticia criada com sucesso ", createdNews);
            setRestResponse("OK");
            setAppError("");
            removeUserAssociated(user)
           
            toast({
            title: 'User associated',
            description: "User "+user.firstName +" associated to project " + projectId,
            status: 'success',
            duration: 5000,
            isClosable: true,
            })
        
                
              
   
          } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setRestResponse("NOK");
            setAppError('error_fetch_login_401');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
                setRestResponse("NOK");

                setAppError(  error );
            }else{
                setRestResponse("NOK");

                setAppError(  "error_fetch_generic" );
            }
        }
    }
    const onError = (errors, e) => console.log(errors, e);
    

    return (<>                
                
            <Tr key={user.id}>
            <Td color="gray.500" fontWeight="400" >
                {user.firstName + " " + user.lastName}  
                <IconButton onClick={()=> navigate("/profile/"+user.id)} aria-label={intl.formatMessage({id: 'go_to'})} icon={<ExternalLinkIcon />} 
                />
            </Td>
            <Td color="gray.500" fontWeight="400" >{user.email}</Td>
            
            <Td color="gray.500" fontWeight="400" >
            <form onSubmit={ handleSubmit (onSubmit, onError)}>
            <FormControl variant='floating' id='job' isInvalid = {errors.userRoleInProject} >                        

                <Input 
                {...register("userRoleInProject", {required: true,setValueAs: (v)=> v.trim()})} 
                type="text" 
                placeholder={intl.formatMessage({id: 'job'})}
                />
                <Input 
                {...register("userId")} 
                type="hidden" 
                value={user.id}
                />
               
                   <Button 
                   mx={5}
                type="submit"
               
                colorScheme="teal"
                >
                    {intl.formatMessage({id: 'associate'})} 
                </Button>
                </FormControl>  
            {(errors.userRoleInProject)? 
            (<FormErrorMessage><FormattedMessage id={"error_missing_userRoleInProject"}  ></FormattedMessage></FormErrorMessage>)
            : null 
            }
            </form>
                
            </Td>
        
        </Tr>
        
    </>


            
        )
    }

export default FormUserToAssoc