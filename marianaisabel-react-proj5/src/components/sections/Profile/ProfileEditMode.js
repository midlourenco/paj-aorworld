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
import EditableControls from "./EditableControls"


const ProfileEditMode=({isAdmin, currentUser,editMode,handleEditClick, handleCancelClick, handleDeleteClick, handleSubmit, onSubmit, onError, errors, register})=>{
    
    const intl = useIntl();
    return (<Box>
        <Stack
    flexDir="column"
    mb="2"
    justifyContent="center"
    alignItems="center"
    >
        <Box minW={{ base: "90%", md: "468px" }} mb={5}>
            
            <form onSubmit={ handleSubmit(onSubmit,onError )} >
                <Box>
                    <Avatar size='2xl' bg="teal.500" mt={"-25%"}  name={currentUser.firstName + " " + currentUser.lastName} src={currentUser.image} />
                </Box>
                <Flex
                spacing={2}
                backgroundColor="whiteAlpha.900"
                boxShadow="md"
                minHeight={"300px"}
                p={10}
                flexDirection={"column"}
                justifyContent={"space-between"}
                mb={5} 
                >
                {/* <Heading color="teal.400"> {intl.formatMessage({id: 'welcome'})}</Heading> */}

                <FormControl variant='floating' id='first-name' isInvalid = {errors.firstName} >                        
                    <Input  {...register("firstName", {required: true})} type="text" id={"firstname"} defaultValue =  {currentUser.firstName}  name="firstName" placeholder={intl.formatMessage({id: 'form_field_first_name'})}/>
                    {/* <Input type="text" fontSize='2xl' fontWeight={"bold"}  palceHolder  = {currentUser.firstName}  /> */}
                    <FormLabel color={"teal.500"} >{intl.formatMessage({id: 'form_field_first_name'})}</FormLabel>
                </FormControl>  
                {(errors.firstName)? 
                (<FormErrorMessage><FormattedMessage id={"error_missing_first_name"}  ></FormattedMessage></FormErrorMessage>)
                : null 
                }
                <FormControl mt={6} variant='floating' id='last-name' isInvalid = {errors.lastName} >                        
                    <Input {...register("lastName", {required: true})}type="text" defaultValue =  {currentUser.lastName}  name="lastName" placeholder={intl.formatMessage({id: 'form_field_last_name'})}  />
                    {/* <Input type="text" fontSize='2xl' fontWeight={"bold"}  palceHolder  = {currentUser.lastName} />
                 */}
                <FormLabel color={"teal.500"} >{intl.formatMessage({id: 'form_field_last_name'})}</FormLabel>
                {(errors.lastName)? 
                (<FormErrorMessage><FormattedMessage id={"error_missing_last_name"}  ></FormattedMessage></FormErrorMessage>)
                : null 
                }
                </FormControl>  
                <FormControl  mt={6} variant='floating' id='email' isInvalid = {errors.email}>     
                    <Input {...register("email", {required: true})}  type="email"  defaultValue =  {currentUser.email}  name="email"  placeholder={intl.formatMessage({id: 'form_field_email'})}/>
                    {/* value  = {currentUser.email }  onChange={handleInputChange} name="email"
                    <Input type="email" as="i" fontSize='md'mt={1} palceHolder  = {currentUser.email }  /> */}
                <FormLabel color={"teal.500"} > {intl.formatMessage({id: 'form_field_email'})}</FormLabel>
                {(errors.email)? 
                (<FormErrorMessage><FormattedMessage id={"error_missing_email"}  ></FormattedMessage></FormErrorMessage>)
                : null 
                }
                </FormControl>  
                <FormControl  mt={6} variant='floating' id='image' >
                <Input {...register("image")}  type="url"  defaultValue = {currentUser.image}  name="image"  placeholder={intl.formatMessage({id: 'form_field_image'})}/>
                {/* {currentUser.image}   */}
                <FormLabel color={"teal.500"} > {intl.formatMessage({id: 'form_field_image'})}</FormLabel>
                </FormControl>
                    <br />
                {isAdmin?
                // (<ButtonGroup mt={4} mb={6} size='sm' isAttached variant='outline' >
                // <Button isDisabled={true}  mr='-px'><FormattedMessage id={currentUser.privileges || "-"}  defaultMessage={"-"} /></Button>
                // <IconButton aria-label='EditRole' icon={<EditIcon />} />
                // </ButtonGroup>)
                (currentUser.privileges =="ADMIN" ?
                    (<Select {...register("privileges", {value: currentUser.privileges})} mt={4} mb={6} size='sm' icon={<EditIcon />} placeholder={currentUser.privileges } defaultValue={currentUser.privileges }   >
                        <option value='MEMBER'>{intl.formatMessage({id: 'MEMBER'})}</option>
                    </Select>)
                    :(<Select {...register("privileges")} mt={4} mb={6} size='sm' icon={<EditIcon />} placeholder={currentUser.privileges } defaultValue={currentUser.privileges }  >
                        <option value='ADMIN'>{intl.formatMessage({id: 'ADMIN'})}</option>
                    </Select>)
                )
                :<Select {...register("privileges")} mt={4} mb={6} size='sm' icon={<EditIcon />} placeholder={currentUser.privileges } defaultValue={currentUser.privileges } isDisabled={true} ></Select>
            //    : <Badge  mt={4} mb={6} mx={2} fontSize={"10px"} color={"teal.400"} ><FormattedMessage id={currentUser.privileges || "-"}  defaultMessage={"-"} /></Badge>
                }
                <FormControl variant='floating' id='biography' > 
                <Textarea {...register("biography")}  defaultValue =  {currentUser.biography} name="biography" placeholder={intl.formatMessage({id: 'form_field_biography'})} /> 
                    {/* <Textarea fontSize='lg' placeholder= {currentUser.biography} /> 
                 */}
                 <FormLabel color={"teal.500"}  >{intl.formatMessage({id: 'form_field_biography'})}</FormLabel>
                </FormControl>
                
                <Box mt={6}>
                    {/* <IconButton size='sm' icon={<EditIcon />} background="whiteAlpha.900" pt={5} >Editar</IconButton> */}
                    <EditableControls isAdmin={isAdmin} editMode={editMode} handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} handleCancelClick={handleCancelClick} />
                    {/* <SubmitButton type={ "submit"} /> */}
                </Box>
               
                </Flex>
            
                </form>
                
                {/* <CancelButton handleCancelClick={handleCancelClick} /> */}
           
        </Box>
    </Stack>
    </Box>
    )
}


export default ProfileEditMode
