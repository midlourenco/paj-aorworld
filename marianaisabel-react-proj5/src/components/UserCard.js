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
import { RiFullscreenExitLine } from 'react-icons/ri';


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
        <Flex border={"1px"} borderColor={"gray.200"} p={2} borderRadius={"20px"}>
        <Avatar src={user.image} my={'auto'}/>
        <Flex  direction="column" w="100%" minHeight={"80px"} alignItems="flex-start" justifyContent="center" mx={3} >
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
            <Text fontSize='sm'>{user.biography}</Text>
        </Flex>
    </Flex>
    )
    }

export default UserCard;