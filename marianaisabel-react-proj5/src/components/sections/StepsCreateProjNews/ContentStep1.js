import React from 'react'
import {FormattedMessage ,useIntl} from "react-intl";
import {
    Flex,
    Input,
    Button,
    InputGroup,
    Stack,
    Box,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    Checkbox,
    Textarea,
    Badge,
    Tag,
    TagLabel,
    InputRightElement,
    TagCloseButton
} from "@chakra-ui/react";
import {  AddIcon } from '@chakra-ui/icons'


function ContentStep1 ({errors,register,saveInputKeyword, input,keywords, handleAddNewKeyword, handleDeleteTag,...props }){
    const intl = useIntl();
    return(<Flex justifyContent={"center"} py={4} width={"100%"}>
      {/* <Text p={1} >step 1 texto </Text> */}
      {/* <form onSubmit={ handleSubmit (onSubmit, onError)}> */}
        <Stack
            spacing={4}
            p={4}
            backgroundColor="whiteAlpha.900"
            boxShadow="md"
            width={"100%"}
        >
            <FormControl isInvalid = {errors.title}>
            <Input {...register("title", {required: true})} type="text" placeholder={intl.formatMessage({id: 'form_field_title'})} />
            {(errors.title)? 
                (<FormErrorMessage><FormattedMessage id={"error_missing_title"}  ></FormattedMessage></FormErrorMessage>)
                : null 
            }
            </FormControl>
    
            <Textarea {...register("description", {required: false})} backgroundColor="whiteAlpha.950" placeholder={intl.formatMessage({id: 'form_field_description'})}  />
            
            <FormControl isInvalid = {errors.keyword}>
            <InputGroup>
            <Input {...register("keywords", {required: true})} type="text" placeholder={intl.formatMessage({id: 'form_field_keywords'})}  onChange={saveInputKeyword} value={input} />
    
            <InputRightElement width="4.5rem">
                <Button h="1.75rem" size="sm" onClick={handleAddNewKeyword}>
                    {/* {showPassword ? "Hide" : "Show"} */}
                    <AddIcon color = "gray.400" />
                </Button>
            </InputRightElement>
        </InputGroup>
        {keywords && keywords.length ?
            (<Box width={"l"} mr={3} spacing={4}> {
            keywords.map(k => ( 
                <Tag key={k} m={2}  colorScheme='teal'>
                <TagLabel>{k}</TagLabel>
                <TagCloseButton onClick={handleDeleteTag} />
                </Tag>
            ))} 
            </Box>)
        :null
        }
        {console.log(keywords) }
    
    
            {(errors.keyword)? 
                (<FormErrorMessage><FormattedMessage id={"error_missing_keyword"}  ></FormattedMessage></FormErrorMessage>)
                : null 
            }
            </FormControl>
    
            <FormControl isInvalid = {errors.image}>
    
            <Input {...register("image")} type= "url" placeholder={intl.formatMessage({id: 'form_field_image'})} />
            
            {/* TODO previsualizar imagem
            <Image
                boxSize='100px'
                objectFit='cover'
                src={register.image}
                alt='project_Image'
            /> */}
    
            {(errors.password)? 
                (<FormErrorMessage><FormattedMessage id={"error_wrong_image"}  ></FormattedMessage></FormErrorMessage>)
                : null 
            }
            
            </FormControl>
    
            {/* <Button
                borderRadius={0}
                type="submit"
                variant="solid"
                colorScheme="teal"
                width="full"
                // onClick={console.log("carreguei em login")}
            >
                {intl.formatMessage({id: 'create_project'})} 
            </Button> */}
    
        </Stack>
    {/* </form> */}
    </Flex>
    )
  };

export default ContentStep1