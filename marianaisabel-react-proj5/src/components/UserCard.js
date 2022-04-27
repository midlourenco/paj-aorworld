import React from 'react'
import {  Link, useParams } from "react-router-dom";

import {
    Flex,
    Heading,
    Grid,
    Link as ChakraLink,
    Box,
    Image,
    Badge,
    Text,
    Avatar
} from "@chakra-ui/react";
import { FormattedMessage ,useIntl} from "react-intl";


function UserCard({user, ...props}) {
    const setColorBadgeRole = ()=>{
        if(user.privileges =="MEMBER"){
            return "teal"
        }else if(user.privileges =="ADMIN"){
            return "yellow"
        }else{
            return "gray"
        }
    }
    let id = user.id;

    return (
        <Flex  >
        <Avatar src={user.image} my={'auto'}/>
        <Box  height={"80px"}  mx={3} minW={"200px"}>
            <ChakraLink as={Link} to ={`/profile/${id}`} >
                <Text fontSize="md" 
                fontWeight='bold'
                my='0'
                isTruncated
            
                > 
                    {user.firstName + " " +user.lastName }
                    <Badge ml='1' colorScheme={setColorBadgeRole()}>
                    <FormattedMessage id={user.privileges} />
                    </Badge>
                </Text>
            </ChakraLink>

            {/* <Text fontWeight='bold'>
                {user.firstName + " " +user.lastName }
                <Badge ml='1' colorScheme={setColorBadgeRole()}>
                {user.role}
                </Badge>
            </Text> */}
            <Text fontSize='sm'>{user.email}</Text>
            <Text fontSize='sm' isTruncated>{user.biography}</Text>
        </Box>
    </Flex>
    )
    }

export default UserCard;