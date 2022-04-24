import React from "react"
import { useState } from "react";
//Redirect replace by Naviagate
import {Navigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";
import { Step, Steps, useSteps } from 'chakra-ui-steps';
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
    Image,
    Box,
    Link,
    Text,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    Textarea,
    InputRightElement
} from "@chakra-ui/react";
import { FaEyeSlash, FaEye} from "react-icons/fa";
import ButtonExemple from "../components/ButtonExemple";


function NewProject() {
  const users = [
    { firstName: 'Mariana', imageURL: "https://bit.ly/dan-abramov" },
    { firstName: 'Isabel', imageURL: "https://bit.ly/dan-abramov"  },
    { firstName: 'José', imageURL: "https://bit.ly/dan-abramov"  },
    { firstName: 'Ricardo', imageURL: "https://bit.ly/dan-abramov"  },
  ];

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

  const { nextStep, prevStep, setStep, reset, activeStep } = useSteps({
    initialStep: 0,
  });


  const content1 = (
    <Flex justifyContent={"center"} py={4}>
      {/* <Text p={1} >step 1 texto </Text> */}
      {/* <form onSubmit={ handleSubmit (onSubmit, onError)}> */}
              <Stack
                  spacing={4}
                  p="1rem"
                  backgroundColor="whiteAlpha.900"
                  boxShadow="md"
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
                  <Input {...register("keywords", {required: true})} type="text" placeholder={intl.formatMessage({id: 'form_field_keywords'})} />
                  {(errors.keyword)? 
                    (<FormErrorMessage><FormattedMessage id={"error_missing_keyword"}  ></FormattedMessage></FormErrorMessage>)
                    : null 
                  }
                </FormControl>
  
                <FormControl isInvalid = {errors.imageURL}>

                  <Input {...register("imageURL")} type= "url" placeholder={intl.formatMessage({id: 'form_field_imageURL'})} />
                  
                  {/* TODO previsualizar imagem
                  <Image
                    boxSize='100px'
                    objectFit='cover'
                    src={register.imageURL}
                    alt='project_Image'
                  /> */}
  
                  {(errors.password)? 
                    (<FormErrorMessage><FormattedMessage id={"error_wrong_imageURL"}  ></FormattedMessage></FormErrorMessage>)
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
  );
  const content2 = (
    <Flex py={4}>
      {/* <Text p={1} >step 2 texto </Text> */}



    </Flex>
  );
  const content3 = (
    <Flex py={4}>
      <Text p={1} >step 3 texto </Text>
    </Flex>
  );
  const steps = [
    { label: 'Project Details', content: content1 },
    { label: 'Associate Users', content: content2 },
    { label: 'Associate News', content: content3 },
  ];


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
    
        <Heading mt={20}  color="teal.400"> {intl.formatMessage({id: 'create_project'})}</Heading>
        <Box minW={{ base: "90%", md: "468px" }}>
          <Flex flexDir="column" width="100%">
          <form onSubmit={ handleSubmit (onSubmit, onError)}>
            <Steps activeStep={activeStep}>
              {steps.map(({ label, content }) => (
                <Step label={label} key={label}>
                  {content}
                </Step>
              ))}
            </Steps>
        
            {activeStep === steps.length ? (
              <Flex p={4}>
                <Button mx="auto" size="sm"  type="submit">
                  Criar projeto
                </Button>
              </Flex>
            ) : (
              <Flex width="100%" justify="flex-end">
                <Button
                  isDisabled={activeStep === 0}
                  mr={4}
                  onClick={prevStep}
                  size="sm"
                  variant="ghost"
                >
                  Prev
                </Button>
                <Button size="sm" onClick={nextStep}>
                  {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                </Button>
              </Flex>
            )}
          </form>
          </Flex>
          <Box>
                <ButtonExemple path="/projects" description="Back to Projects" />
            </Box>
        </Box>
      </Stack>
    
    </Flex>
  );
}

export default NewProject;