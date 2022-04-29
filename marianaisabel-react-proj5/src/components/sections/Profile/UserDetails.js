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
    Badge,
    Text,
    Stack
   
} from "@chakra-ui/react";


function UserDetails({currentUser,...props}) {
    const intl = useIntl();
  return (
    <>
        <Heading mt={10} as="h3" ><FormattedMessage id={"personal_details"} />:</Heading>

        <Stack justifyContent={"center"} margin={"auto"} >
        <Flex direction="column">
            <Text fontSize="md" color="gray.500" fontWeight="400" mb="30px">
            {currentUser.biography}
            </Text>
            <Flex alignItems="center" mb="18px">
                <Text fontSize="md" fontWeight="bold" me="10px">
                {intl.formatMessage({id: 'full_name'})}{" "}
                </Text>
                <Text fontSize="md" color="gray.500" fontWeight="400">
                {currentUser.firstName +" "+ currentUser.lastName}
                </Text>
            </Flex>
            <Flex alignItems="center" mb="18px">
                <Text fontSize="md" fontWeight="bold" me="10px">
                    {intl.formatMessage({id: 'email'})}{" "}
                </Text>
                <Text fontSize="md" color="gray.500" fontWeight="400">
                    {currentUser.email }
                </Text>
            </Flex>
            <Flex alignItems="center" mb="18px">
                <Text fontSize="md" fontWeight="bold" me="10px">
                    {intl.formatMessage({id: 'role'})}{" "}
                </Text>
                <Text fontSize="md" color="gray.500" fontWeight="400">
                    <Badge mx={2} fontSize={"10px"} color={"teal.400"} ><FormattedMessage id={currentUser.privileges || "-"} defaultMessage={"-"} /></Badge>
                </Text>
            </Flex>
        </Flex>
        </Stack>
    </>
  )
}

export default UserDetails


{/* <Box>
{/* {console.log(currentUser)}                             */}
//     <Text fontSize='2xl' fontWeight={"bold"}> </Text>
//     <Text as="i" fontSize='md'mt={1}  > {currentUser.email }</Text>
//     <br />
//    </Box>
// <Box>
//     <
// </Box> */}