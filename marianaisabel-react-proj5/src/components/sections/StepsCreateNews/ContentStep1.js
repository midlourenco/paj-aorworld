import React, {useState} from 'react'
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
    TagCloseButton,
    Radio, 
    RadioGroup ,
} from "@chakra-ui/react";
import {  AddIcon } from '@chakra-ui/icons'
import { set } from 'react-hook-form';


function ContentStep1 ({errors, register,nextStep, trigger,saveInputKeyword, input,keywords, setShowStepperButtons, showStepperButtons, handleAddNewKeyword, handleDeleteTag,...props }){
    const intl = useIntl();

    const [visibility, setVisibility] = useState("1")

    return(    <Flex justifyContent={"center"} py={4} width={"100%"}>
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
            <Input {...register("title", {required: true, setValueAs: (v)=> v.trim()})} type="text" placeholder={intl.formatMessage({id: 'form_field_title'})} />
            {(errors.title)? 
                (<FormErrorMessage><FormattedMessage id={"error_missing_title"}  ></FormattedMessage></FormErrorMessage>)
                : null 
            }
            </FormControl>
            <FormControl isInvalid = {errors.description}>
            <Textarea {...register("description", {required: true, setValueAs: (v)=> v.trim()})} backgroundColor="whiteAlpha.950" placeholder={intl.formatMessage({id: 'form_field_description'})}  />
            {(errors.description)? 
                (<FormErrorMessage><FormattedMessage id={"error_missing_description"}  ></FormattedMessage></FormErrorMessage>)
                : null 
            }
            </FormControl>
            <FormControl isInvalid = {errors.keyword}>
            <InputGroup>
            <Input {...register("keywords", {deps: keywords})} type="text" placeholder={intl.formatMessage({id: 'form_field_keywords'})}  onChange={saveInputKeyword} value={input} />
    
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
                <TagCloseButton onClick={()=>handleDeleteTag(k)} />
               {/* z <TagCloseButton onClick={()=>remove(index)} /> */}
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
    
            {(errors.image)? 
                (<FormErrorMessage><FormattedMessage id={"error_wrong_image"}  ></FormattedMessage></FormErrorMessage>)
                : null 
            }
            
            </FormControl>

        
                
            <FormControl >  
            <RadioGroup 
            onChange={setVisibility} 
            value={visibility}
            name="visibility"
            >
                <Stack direction='row'>
                    <Radio  {...register("visibility")}  value="1">{intl.formatMessage({id: 'public'})}</Radio>
                    <Radio  {...register("visibility")}  value="2">{intl.formatMessage({id: 'private'})}</Radio>
                </Stack>
            </RadioGroup>
           
            
            </FormControl>
                

            <Flex width="100%" justify="flex-end">
            <Button
                type="button"
                size="sm"
                
                onClick={async () => {
                    setShowStepperButtons(!showStepperButtons)
                    const result = await trigger(["title", "description","image", "visibility"]);
                    // console.log(result)
                   
                    if(result){
                        nextStep();
                    }
                }}
            > {intl.formatMessage({id: 'next'})} 
            </Button>
            </Flex>
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