import React, { useEffect, useState } from 'react'

import {
    ChakraProvider,
    Flex,
    Text,
    Link as ChakraLink,
    Box,
    Grid,
    Image,
    AspectRatio,
    Heading,
    Stack,
    Spacer,
    HStack,
    Select,
    Button,
    StackDivider
  } from "@chakra-ui/react";

  import {FormattedMessage} from "react-intl";
  import UserCard from "../components/UserCard"
  import useFetch from 'use-http';

  //TODO: 
function setAppError(error){
    console.log(error)
}
const AboutUs = ()=>{
    const user1={
        id: "9999",
        firstName:  "Carlos",
        lastName: "Pita",
        email:  "carlospita@aorprojects.pt",
        biography:  "uieyish ashdjkho  ewhiorh whro iwo3",
        image: "https://bit.ly/dan-abramov",
        privileges: "MEMBER"
    }


    const user2={
        id: "999",
        firstName:  "Manuel",
        lastName: "Shoarma",
        email:  "manuelshoarma@aorprojects.pt",
        biography:  "uieyish ashdjkho  ewhiorh whro iwo3",
        image: "https://bit.ly/sage-adebayo",
        privileges: "ADMIN"


    }
    const [users, setUsers]=useState([])

    const { get, post, response, loading, error } = useFetch();
    useEffect(async()=>{
        

        const getUsers = await get('users')
        if (response.ok) {
            console.log(getUsers)
            setUsers(getUsers);
            setUsers(prevState=>[...prevState, user1]);
            setUsers(prevState=>[...prevState, user2]);
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





    return (
        <Stack 
        spacing={10} 
        backgroundColor="gray.200" 
        pb={20}
        minHeight="83vh"
        >            
        {/* <Flex  flexDirection="column" justifyContent="center" alignItems="start"  mr={5}>
            <Flex display="Flex" justifyContent="space-between" width={"100%"}> */}

    
        <Box minW={{ base: "90%", md: "468px" }} mb={0} >
            <Flex
            spacing={2}
            backgroundColor="whiteAlpha.900"
            boxShadow="md"
            minHeight={"300px"}
            p={[5,5,10]}
            flexDirection={"column"}
            justifyContent={"space-between"}
            with={"80%"}
            mt={5}
            mx={20}
            >
                    <Heading> <FormattedMessage id={"our_team"} /> </Heading>
            {error && 'Error!'}
            {loading && 'Loading...'}
            <Grid  px={5} templateColumns={['1fr', 'repeat(2, 1fr)', 'repeat(3, 1fr)']} gap={8} mx={5} >
                {users.map(u =>(<UserCard user ={u} key={u.id} ></UserCard>))}
            </Grid>

        </Flex>
    </Box>

           
            <Box minW={{ base: "90%", md: "468px" }} mb={0} >
            <Flex
            spacing={2}
            backgroundColor="whiteAlpha.900"
            boxShadow="md"
            minHeight={"300px"}
            p={[5,5,10]}
            flexDirection={"column"}
            justifyContent={"center"}
            alignContent={"center"}
            with={"80%"}
            mt={5}
            mx={20}
            > 

            <Heading> <FormattedMessage id={"where_are_we"} /> </Heading>   
            <AspectRatio ratio={21 / 9} maxW='60%' mx={20}  alignContent={"center"}>
            <iframe 
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d16751.422199276334!2d-8.433388030438046!3d40.18608913770532!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd22f9952aaf6517%3A0xd3822ec1d359eb52!2sDEI%20-%20Department%20of%20Informatics%20Engineering%20-%20FCTUC!5e0!3m2!1sen!2spt!4v1650556009804!5m2!1sen!2spt" 
            loading="lazy" 
            referrerPolicy="no-referrer-when-downgrade"
            alt="localizacao"
            ></iframe>
            </AspectRatio>
           
            
            
            </Flex>
    </Box>
        </Stack>
    )
    
}
export default AboutUs;