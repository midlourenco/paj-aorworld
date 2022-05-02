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
    Image,
    Text,
    HStack,
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
import AssocNewsTab from "./AssocNewsTab"
import AssocUsersTab from "./AssocUsersTab"


const ProjectDetails=({isAdmin, currentProject,...props})=>{
    const navigate = useNavigate();
    const intl = useIntl();
 
    return (<>
                <Box  alignSelf={"center"}>
                    {console.log(currentProject)}
                        {/* <Image boxSize='200px' name={currentProject.title} src={currentProject.image} ></Image>            
                    */}
                        {currentProject.deleted?
                    (<Box>
                    <Badge colorScheme='red'><FormattedMessage id={"deleted"} /> </Badge>
                    <Image 
                    src={currentProject.image} 
                    fallbackSrc="../images/logo.png"
                    alt="project_image" 
                    h='255px' 
                    style={{opacity: 0.2}} 
                    />
                    </Box>)
                    : <Image src={currentProject.image} alt="project_image" h='255px' fallbackSrc="../images/logo.png"  />
                    }
                </Box>
                <Box  textAlign={"center"} mb={7}>
                    <Text fontSize='2xl' my={"30px"} me="10px" fontWeight={"bold"}> {currentProject.title }</Text>

                   
                    <Text fontSize="md" color="gray.500" fontWeight="400" mb="30px"> {currentProject.description }</Text>
                    
                    <Box  mb='5' alignSelf={"center"} justifyContent={"center"}>
                    {currentProject.deleted?
                        currentProject.keywords.map(k => (
                        <Badge borderRadius='full' px='2' color='grey' key={k} >{k}</Badge>
                        ))
                    : currentProject.keywords.map(k => (
                        <Badge borderRadius='full' px='2' colorScheme='teal' key={k} >{k}</Badge>
                        ))
                    }
                    </Box>

                    {currentProject.visibility?
                        <Badge borderRadius='full' px='2' color='teal' > {intl.formatMessage({id: 'public'})  }   </Badge>
                        : <Badge borderRadius='full' px='2' color='green' > {intl.formatMessage({id: 'private'})  }   </Badge>
                    }


                    <Text fontSize="md" fontWeight="bold" me="10px"> <FormattedMessage id={"create_by"} /> <Text  as='i' fontSize='sm' ml={1}  color="gray.500" fontWeight="400" mb="30px"> {currentProject.createdBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(currentProject.createdDate)}} />  </Text> </Text>
                    {currentProject.lastModifDate!=="" && currentProject.lastModifDate!=null ?
                    <Text fontSize="md" fontWeight="bold" me="10px"> <FormattedMessage id={"update_by"} /> <Text  as='i' fontSize='sm' ml={1}  color="gray.500" fontWeight="400" mb="30px">  {currentProject.lastModifBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(currentProject.lastModifDate)}} />   </Text>     </Text>                      
                    :null
                    }
                </Box>

            </> 

        )
    }

export default ProjectDetails