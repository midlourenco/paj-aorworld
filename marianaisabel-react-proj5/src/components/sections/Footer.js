import React from "react"
import { Box, Flex,Divider, Text, Button, Stack } from "@chakra-ui/react"

function Footer(){


    return(
        <Box >
            <Divider orientation='horizontal' borderColor="teal.400" borderWidth={1}/>
            <Flex 
                justifyContent={"right"}
                background="gray.200"
                alignSelf={"right"}
                
                >
                
            <Text  fontSize='sm' padding="10px"> © 2022 Mariana Lourenço | PAJ - Proj5</Text>
            </Flex>
        </Box>

    )

}

export default Footer;