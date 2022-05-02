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
    Stack,
    Box,
} from "@chakra-ui/react";
import { ExternalLinkIcon, CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../../EditableControls"
import AssocNewsTab from "./AssocNewsTab"
import AssocUsersTab from "./AssocUsersTab"
import ProjectDetails from './ProjectDetails';


const ProjectViewMode=({canEdit,isAdmin,userId, currentProject,editMode,handleEditClick,handleCancelClick, handleDeleteClick})=>{
    const navigate = useNavigate();
    const intl = useIntl();
    return (<Box>
     
        <Stack
        flexDir="column"
        mb="2"
        justifyContent="center"
        alignItems="center"
        >
            
        <Box minW={{ base: "90%", md: "468px" }} mb={5}>
            <Flex
            spacing={2}
            backgroundColor="whiteAlpha.900"
            boxShadow="md"
            minHeight={"300px"}
            p={10}
            flexDirection={"column"}
            justifyContent={"space-between"}
            mt={10}
            >
                    <ProjectDetails currentProject={currentProject} isAdmin={isAdmin}  />


                    {canEdit?
                    (<Box >
                        <EditableControls  isAdmin={isAdmin} editMode={editMode}  handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} handleCancelClick={handleCancelClick} />
                    </Box>
                    ) :currentProject.deleted && (userId==currentProject.createdBy.id || isAdmin ) ?
                        <Flex justifyContent='center'>
                            <Button colorScheme={"teal"} size="md" >{intl.formatMessage({id: 'undelete'})} </Button>
                        </Flex >
                            :null
                    }


                    <AssocUsersTab  canEdit={canEdit} currentProject={currentProject} isAdmin={isAdmin} />
                    {canEdit?
                    (<Box  textAlign={"center"}>
                        <Button  mt={5} colorScheme={"teal"} size="md"  onClick={()=>{navigate(`/projects/${currentProject.id}/associateusers`)}} >  {intl.formatMessage({id: 'associate_users'})}</Button>
                    </Box>
                    ) :null
                    }

                    <AssocNewsTab canEdit={canEdit}  currentProject={currentProject} isAdmin={isAdmin} />


                </Flex>
                    
            </Box>
        </Stack>
    </Box>
        )
    }

export default ProjectViewMode