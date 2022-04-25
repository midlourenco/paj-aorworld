import React from 'react'
import {  Link, useParams } from "react-router-dom";

import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    InputLeftElement,
    chakra,
    Grid,
    Link as ChakraLink,
    Box,
    Image,
    Badge,
    Text,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    InputRightElement,
    HStack,
    Spacer
} from "@chakra-ui/react";


function UserCard({user, ...props}) {
    const setColorBadgeRole = ()=>{
        if(user.role =="MEMBER"){
            return "teal"
        }else if(user.role =="ADMIN"){
            return "yellow"
        }else{
            return "gray"
        }
    }
    let id = user.id;

    return (
        <Flex>
        <Avatar src={user.imageURL} />
        <Box ml='3'>
        <ChakraLink as={Link} to ={`/profile/${id}`}  >
            <Text fontSize="md"  mt='1'
            fontWeight='bold'
            mb='1'
            isTruncated
            > 
                {user.firstName + " " +user.lastName }
                <Badge ml='1' colorScheme={setColorBadgeRole()}>
                {user.role}
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