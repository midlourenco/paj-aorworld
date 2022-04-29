import React from 'react'
import { useState, useEffect, } from "react";
//Redirect replace by Naviagate
import {Navigate, useParams, useNavigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";

import {
    Flex,
    Text,
    Button,
    ButtonGroup,
    Tooltip,
    Heading,
    Table,
    Thead,
    Tbody,
    Tfoot,
    Tr,
    Th,
    Td,
    TableContainer,
} from "@chakra-ui/react";
import { CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../../EditableControls"
import { FaUserTimes,  FaUserCheck} from 'react-icons/fa';


//TODO: 
function setAppError(error){
    console.log(error)
}

function DeletedNews({errorTopBar="",isAdmin, userId, currentUser,editMode,handleEditClick,handleCancelClick, handleDeleteClick,...props}){
    const intl = useIntl();
    const { get, post, del, response, loading, error } = useFetch();
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    //const [projectsCreatedByMe,setProjectsCreatedByMe]=useState([])
    // const [projectsAssocToMe,setProjectsAssocToMe]=useState([])
    //const [newsAssocToMe,setNewsAssocToMe]=useState([])
    // const [newsCreatedByMe,setNewsCreatedByMe]=useState([])
    const [deletedNewsAssocToMe,setDeletedNewsAssocToMe]=useState([])
    // const [deletedProjectsAssocToMe,setDeletedProjectsAssocToMe]=useState([])

    useEffect( async() => {
        console.log("houve refresh vou buscar notif " );    
        setRestResponse("");

    
        const deletedNewsAssocToMe = await get('/news/deletedList?user=' + userId);
        if (response.ok) {
            (console.log("noticias assoc" + response))
            setDeletedNewsAssocToMe(deletedNewsAssocToMe);
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
        <Heading pt={20} as="h3" ><FormattedMessage id={"my_deleted_news"} />:</Heading>

            <TableContainer>
            <Table >
            <Thead>
            <Tr>
                    <Th>{intl.formatMessage({id: 'form_field_title'})}</Th>
                    <Th>{intl.formatMessage({id: 'create_by'})}</Th>
                    <Th><FormattedMessage id={"form_field_create_date"}/></Th>
                    <Th>{intl.formatMessage({id: 'desassociate'})}</Th>
            </Tr>
            </Thead>
            <Tbody>
            {deletedNewsAssocToMe.map((n)=>(
            <Tr key={n.id}>
                <Td>{n.title}</Td>
                <Td>{n.createdBy.firstName}</Td>
                <Td textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(n.createdDate)}} /> </Td>
                <Td>
                <ButtonGroup>
                    <Tooltip label={intl.formatMessage({id: 'tooltip_desassociate'})}>
                        <Button maxW="130px" colorScheme={"teal"} fontSize="sm" leftIcon={<FaUserTimes />} > <FormattedMessage id={"desassociate"}/></Button>
                    </Tooltip>
                </ButtonGroup>
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
  
  
  export default  connect(mapStateToProps, {})(DeletedNews)

