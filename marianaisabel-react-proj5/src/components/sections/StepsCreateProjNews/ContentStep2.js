import React, {useEffect, useState} from 'react'
import {FormattedMessage ,useIntl} from "react-intl";
import {
    Flex,
    Stack,
    Box,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    Checkbox,
    Avatar,
    Grid,
    Tag,
    TagLabel,
} from "@chakra-ui/react";
import {  AddIcon } from '@chakra-ui/icons'
import useFetch from 'use-http';
// import { setAppError } from '../redux/actions'
import { connect } from 'react-redux'



function ContentStep2() {
    //função para fazer o request ao servidor
    const { get, post, response, loading, error } = useFetch();
  
    //TODO: 
    function setAppError(error){
    console.log(error)
    }
    const [users, setUsers]=useState([]);
    useEffect(async()=>{
        const getUsers = await get('/users')
        if (response.ok) {
            console.log("dentro do ok do get users vindos do rest  - setp2")
            setUsers(getUsers);
            setAppError("");
            //setLogin(true);
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

    return(<Flex justifyContent={"center"} py={4}  width={"100%"}>
        {error && 'Error!'}
        {loading && 'Loading...'}
    <Stack
    spacing={4}
    p={4}
    backgroundColor="whiteAlpha.900"
    boxShadow="md"
    width={"100%"}
    >
        
        <FormControl >
            <Grid  templateColumns='repeat(2, 1fr)' >
            {users.map(u => (
                <Checkbox key={u.id} colorScheme='teal' m={3} >
                <Tag size='lg' colorScheme='teal' borderRadius='full' variant="outline">
                    <Avatar
                    src={u.image}
                    size='xs'
                    name={u.firstName}
                    ml={-1}
                    mr={2}
                    />
                    <TagLabel>{u.firstName}</TagLabel>
                </Tag>
                </Checkbox>
            ))}  
            </Grid>        
        </FormControl>
        </Stack>
    </Flex>
    )
}

export default ContentStep2