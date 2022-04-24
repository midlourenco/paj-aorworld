import React from "react"
import { useState } from "react";
//Redirect replace by Naviagate
import {Navigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";

import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    HStack,
    InputLeftElement,
    chakra,
    Box,
    Link,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    Textarea,
    InputRightElement
} from "@chakra-ui/react";
import { FaEyeSlash, FaEye} from "react-icons/fa";




const Register = ()=>{
  const EyeSymbol = chakra(FaEye);
  const EyeSlashSymbol = chakra(FaEyeSlash);

  const {register, handleSubmit, formState: {errors}}= useForm();
  //const onSubmit = values => console.log(values); 
  //const handleSubmit(data){setData(data), console.log(data) )};

  const onSubmit = (data, e) => console.log(data, e);
  const onError = (errors, e) => console.log(errors, e);



  const [data, setData]= useState("");
  //mostrar e esconder a password
  const [showPassword, setShowPassword] = useState(false);
  //função que chamamos ao clicar em "show"/"hide" password - altera o state de false para true e vice-versa
  const handleShowClick = () => setShowPassword(!showPassword);

  //https://stackoverflow.com/questions/39630620/react-intl-how-to-use-formattedmessage-in-input-placeholder
  const intl = useIntl();

  return (
    <Flex
        flexDirection="column"
        width="100wh"
        minHeight="90vh"
        backgroundColor="gray.200"
        justifyContent="center"
        alignItems="center"
    >
      <Stack
          flexDir="column"
          mb="2"
          justifyContent="center"
          alignItems="center"
      >
    
        <Heading mt={20}  color="teal.400"> {intl.formatMessage({id: 'create_account'})}</Heading>
        <Box minW={{ base: "90%", md: "468px" }}>
          <form onSubmit={ handleSubmit (onSubmit, onError)}>
            <Stack
                spacing={4}
                p="1rem"
                backgroundColor="whiteAlpha.900"
                boxShadow="md"
            >
              <FormControl isInvalid = {errors.firstName}>
                <Input {...register("firstName", {required: true})} type="text" placeholder={intl.formatMessage({id: 'form_field_first_name'})} />
                {(errors.email)? 
                  (<FormErrorMessage><FormattedMessage id={"error_missing_first_name"}  ></FormattedMessage></FormErrorMessage>)
                  : null 
                }
              </FormControl>

              <FormControl isInvalid = {errors.lastName}>
                <Input {...register("lastName", {required: true})} type="text" placeholder={intl.formatMessage({id: 'form_field_last_name'})} />
                {(errors.email)? 
                  (<FormErrorMessage><FormattedMessage id={"error_missing_last_name"}  ></FormattedMessage></FormErrorMessage>)
                  : null 
                }
              </FormControl>


              <FormControl isInvalid = {errors.email}>
                <Input {...register("email", {required: true})} type="email" placeholder={intl.formatMessage({id: 'form_field_email'})} />
                {(errors.email)? 
                  (<FormErrorMessage><FormattedMessage id={"error_missing_email"}  ></FormattedMessage></FormErrorMessage>)
                  : null 
                }
              </FormControl>
              <FormControl isInvalid = {errors.password}>
                  <InputGroup>
                      <Input
                          {...register("password", {required: true})}
                          type={showPassword ? "text" : "password"}
                          placeholder={intl.formatMessage({id: 'form_field_password'})}
                      />
                      <InputRightElement width="4.5rem">
                          <Button h="1.75rem" size="sm" onClick={handleShowClick}>
                              {/* {showPassword ? "Hide" : "Show"} */}
                              {showPassword ? <EyeSlashSymbol color = "gray.400" /> :<EyeSymbol color = "gray.300" /> }
                          </Button>
                      </InputRightElement>
                  </InputGroup>
                  {(errors.password)? 
                    (<FormErrorMessage><FormattedMessage id={"error_missing_password"}  ></FormattedMessage></FormErrorMessage>)
                    : null 
                  }
              </FormControl>
              
              <FormControl isInvalid = {errors.password}>
                <Input
                    {...register("password", {required: true})}
                    type= "password"
                    placeholder={intl.formatMessage({id: 'form_field_repeat_password'})}
                />
              
                {(errors.password)? 
                  (<FormErrorMessage><FormattedMessage id={"error_missing_password"}  ></FormattedMessage></FormErrorMessage>)
                  : null 
                }
              </FormControl>

              <Textarea backgroundColor="whiteAlpha.950" placeholder={intl.formatMessage({id: 'form_field_biography'})}  />
              

              <FormControl isInvalid = {errors.imageURL}>
              <HStack>
                <Input
                    {...register("imageURL")}
                    type= "url"
                    placeholder={intl.formatMessage({id: 'form_field_imageURL'})}
                />
                <Avatar name={register.firstName & " " & register.lastName} src={register.imageURL} />

                {(errors.password)? 
                  (<FormErrorMessage><FormattedMessage id={"error_wrong_imageURL"}  ></FormattedMessage></FormErrorMessage>)
                  : null 
                }
              </HStack>  
              </FormControl>

              <Button
                  borderRadius={0}
                  type="submit"
                  variant="solid"
                  colorScheme="teal"
                  width="full"
                  // onClick={console.log("carreguei em login")}
              >
                  {intl.formatMessage({id: 'create_account'})} 
              </Button>
        
            </Stack>
          </form>
        </Box>
      </Stack>
      <Box mb={20}>
        <Link color="teal.500" href="/login">
        {intl.formatMessage({id: 'login_existent_account'})}
        </Link>
      </Box>
    </Flex>
  );
    
    }

    export default Register;