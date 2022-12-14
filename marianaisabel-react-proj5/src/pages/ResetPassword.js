import React from "react"
import { useState , useRef} from "react";
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



const ResetPassword = ()=>{
  const EyeSymbol = chakra(FaEye);
  const EyeSlashSymbol = chakra(FaEyeSlash);

  const {register, watch,handleSubmit, formState: {errors}}= useForm();
  //const onSubmit = values => console.log(values); 
  //const handleSubmit(data){setData(data), console.log(data) )};

  const onSubmit = (data, e) => console.log(data, e);
  const onError = (errors, e) => console.log(errors, e);


  const password = useRef({});
  password.current = watch("password", "");
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
        minHeight="83vh"
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
    
        <Heading mt={20}  color="teal.400"> {intl.formatMessage({id: 'reset_password'})}</Heading>
        <Box minW={{ base: "90%", md: "468px" }}>
          <form onSubmit={ handleSubmit (onSubmit, onError)}>
            <Stack
                spacing={4}
                p="1rem"
                backgroundColor="whiteAlpha.900"
                boxShadow="md"
            >


              <FormControl isInvalid = {errors.email}>
                <Input {...register("email", {required: true,setValueAs: (v)=> v.trim()})} type="email" placeholder={intl.formatMessage({id: 'form_field_email'})} />
                {(errors.email)? 
                  (<FormErrorMessage><FormattedMessage id={"error_missing_email"}  ></FormattedMessage></FormErrorMessage>)
                  : null 
                }
              </FormControl>
              <FormControl isInvalid = {errors.password}>
                  <InputGroup>
                      <Input
                          {...register("password", {required: true
                          // minLength: {
                          //   value: 6,
                          //   message: "Password must have at least 6 characters"}
                          })}
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
              
              <FormControl isInvalid = {errors.password2}>
                <Input
                    {...register("passowrd2",{ validate: value =>
                        value === password.current || intl.formatMessage({id: "error_wrong_password"})
                    })}
                    type= "password"
                    placeholder={intl.formatMessage({id: 'form_field_repeat_password'})}
                />
              
                {(errors.password2)? 
                  (<FormErrorMessage><FormattedMessage id={"error_wrong_password"}  ></FormattedMessage></FormErrorMessage>)
                  : null 
                }
              </FormControl>
              <Button
                borderRadius={0}
                type="submit"
                variant="solid"
                colorScheme="teal"
                width="full"
              >
                {intl.formatMessage({id: 'reset_password'})} 
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
    export default ResetPassword;