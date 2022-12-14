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
    Table,
    Thead,
    Tbody,
    Text,
    Tfoot,
    IconButton,
    Tooltip,
    Tr,
    Th,
    Td,
    TableCaption,
    TableContainer,
} from "@chakra-ui/react";
import { ExternalLinkIcon } from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../../EditableControls"
import { FaUserTimes,  FaUserCheck} from 'react-icons/fa';


//TODO: 
function setAppError(error){
    console.log(error)
}

const MyProjects=({errorTopBar="",isAdmin, userId, idToResquest,currentUser,editMode,handleEditClick,handleCancelClick, handleDeleteClick,...props})=>{
    const intl = useIntl();
    const navigate = useNavigate();
    const { get, post, del, response, loading, error } = useFetch();
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    const [projectsCreatedByMe,setProjectsCreatedByMe]=useState([])
    // const [projectsAssocToMe,setProjectsAssocToMe]=useState([])
    // const [newsAssocToMe,setNewsAssocToMe]=useState([])
    // const [newsCreatedByMe,setNewsCreatedByMe]=useState([])
    // const [deletedNewsAssocToMe,setDeletedNewsAssocToMe]=useState([])
    // const [deletedProjectsAssocToMe,setDeletedProjectsAssocToMe]=useState([])

    useEffect( async() => {
        console.log("houve refresh vou buscar notif " );    
        setRestResponse("");

        const projectsCreatedByMe = await get('/projects?user=' + idToResquest);
        if (response.ok) {
            (console.log("proj criados" +response))
            setProjectsCreatedByMe(projectsCreatedByMe);;
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_generic');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
            setAppError(  error);
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }
        
       
    },[])





    return (<>            
        <Heading pt={20} as="h3" ><FormattedMessage id={"my_projects"} />:</Heading>

        <TableContainer>
            <Table >
                <Thead>
                <Tr>
                    <Th>{intl.formatMessage({id: 'form_field_title'})}</Th>
                    <Th>{intl.formatMessage({id: 'create_by'})}</Th>
                    <Th><FormattedMessage id={"form_field_create_date"}/></Th>
                    <Th><FormattedMessage id={"go_to"}/></Th>
                </Tr>
                </Thead>
                <Tbody>
                {projectsCreatedByMe.map((p)=>(
                <Tr key={p.id}>
                    <Td>{p.title}</Td>
                    <Td>{p.createdBy.firstName}</Td>
                    <Td textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(p.createdDate)}} /> </Td>
                    <Td>
                    <Tooltip label={"/projects/" + p.id}>
                        <IconButton onClick={()=> navigate("/projects/"+p.id)} aria-label= {intl.formatMessage({id: 'go_to'})} icon={<ExternalLinkIcon />} />
                    </Tooltip>
                    </Td>
                </Tr>
                ))}  
                </Tbody>
            </Table>
        </TableContainer>
        {restResponse=="OK"?
        <Text mt={5} color="green"><FormattedMessage id={"sucess_on_updating"} /> </Text>
        : restResponse=="NOK"?
        <Text mt={5} color="red"> <FormattedMessage id={"error_on_updating"} />  </Text>
        :null
        }
    </>

    )
}
const mapStateToProps = state => {
    return {userPriv: state.loginOK.userPriv,
            userId: state.loginOK.userId,
            errorTopBar: state.errorMsg.errorTopBar,
    };
  };
  
  
  export default  connect(mapStateToProps, {})(MyProjects)


