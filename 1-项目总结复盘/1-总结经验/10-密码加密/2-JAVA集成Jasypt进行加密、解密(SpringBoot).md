JAVA (SpringBoot) 集成 Jasypt 进行加密、解密 - 详细教程
在开发过程中，我们经常需要处理敏感数据，如数据库密码、API 密钥等。为了确保这些数据的安全性，我们可以使用加密技术来保护它们不被泄露。Jasypt（Java Simplified Encryption）是一个简单易用的加密库，非常适合用来保护配置文件中的敏感数据。本文将详细介绍如何在 Spring Boot 项目中集成 Jasypt，并展示加密、解密的实际使用方式。

一、Jasypt 简介
Jasypt 是 Java 平台的简化加密工具，支持对文本和数据进行加密和解密，尤其适合应用于 Spring Boot 项目的配置文件加密。其主要特点包括：

简单易用的 API
支持对属性文件内容加密
支持常见的加密算法
与 Spring Boot 的无缝集成
二、在 Spring Boot 中集成 Jasypt
1. 添加 Jasypt 依赖
   在 pom.xml 文件中添加 Jasypt 依赖：

<dependency>
<groupId>com.github.ulisesbocchio</groupId>
<artifactId>jasypt-spring-boot-starter</artifactId>
<version>3.0.4</version> <!-- 请根据需要更新到最新版本 -->
</dependency>
   
   如果使用的是 Gradle，则添加如下依赖：

implementation 'com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4'

2. 配置加密密钥
   为了加密和解密数据，Jasypt 需要一个密钥（secret key）。你可以将这个密钥配置在 Spring Boot 的 application.properties 或 application.yml 文件中：

application.properties 配置：

jasypt.encryptor.password=mysecretkey

application.yml 配置：

jasypt:
encryptor:
password: mysecretkey

注意：mysecretkey 是你用于加密和解密的密钥，请确保密钥的安全性，避免公开泄露。可以通过环境变量或其他安全手段来管理该密钥。

3. 加密敏感数据
   假设我们需要对数据库密码进行加密，首先，我们需要生成加密后的密码。

3.1 使用 Jasypt 命令行工具加密
   Jasypt 提供了命令行工具来加密数据，执行以下命令加密数据库密码：

java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="your_password" password="mysecretkey" algorithm=PBEWithMD5AndDES

其中：

input 是要加密的文本（例如数据库密码）
password 是加密密钥（与我们在配置文件中设置的 mysecretkey 一致）
algorithm 是加密算法，这里使用 PBEWithMD5AndDES。
执行后，将得到类似以下的加密文本输出：

5MG1sSxAx+EeJsBdmcVdKg==

3.2 将加密后的密码写入配置文件
加密后的密码需要在配置文件中使用 ENC() 包裹。例如，假设加密的是数据库密码：

application.properties：

spring.datasource.password=ENC(5MG1sSxAx+EeJsBdmcVdKg==)

application.yml：

spring:
datasource:
password: ENC(5MG1sSxAx+EeJsBdmcVdKg==)

Jasypt 会在应用启动时自动解密使用 ENC() 包裹的加密值。

4. 启动应用并解密数据
   当 Spring Boot 项目启动时，Jasypt 会自动识别 ENC() 包裹的加密内容，并使用密钥进行解密，无需额外的代码逻辑。这意味着在运行时，应用能够使用解密后的值。

三、自定义 Jasypt 加密配置
Jasypt 支持多种加密算法和配置项。如果你需要使用不同的加密算法、密钥生成方式等，可以通过 Java 配置进行自定义。

1. 自定义加密器配置
   通过自定义 StringEncryptor，我们可以配置加密算法、密钥迭代次数等：


import org.jasypt.encryption.StringEncryptor;    
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;   
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;   
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;

@Configuration  
public class JasyptConfig {

    @Bean
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        // 配置加密密钥，务必保密
        config.setPassword("mysecretkey");
        // 设置加密算法
        config.setAlgorithm("PBEWithMD5AndDES");
        // 设置密钥迭代次数，影响破解难度
        config.setKeyObtentionIterations("1000");
        // 设置加密池的大小，1 表示单个实例使用
        config.setPoolSize("1");
        // 盐生成器类，防止彩虹表攻击
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        // 设置输出编码类型为 base64
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}


2. 参数说明：
   Password: 加密和解密时使用的密钥。应与配置文件中的 jasypt.encryptor.password 保持一致。
   Algorithm: 加密算法。可以选择 PBEWithMD5AndDES、PBEWithSHA1AndDESede 等。
   KeyObtentionIterations: 密钥生成的迭代次数，值越高，破解难度越大。
   SaltGeneratorClassName: 盐生成器，防止使用预计算好的哈希表（彩虹表）破解。
   StringOutputType: 设置输出的编码方式。通常使用 base64 或 hexadecimal。
   四、加密与解密的详细示例
   以下是一个详细的加密与解密的单元测试示例，用于测试自定义加密器的加密和解密功能。

import org.jasypt.util.text.BasicTextEncryptor;   
import org.junit.jupiter.api.Test;   
import static org.junit.jupiter.api.Assertions.*;    

public class JasyptEncryptionTest {

    @Test
    public void testEncryptionDecryption() {
        // 创建 BasicTextEncryptor 对象进行简单的加密解密操作
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        // 设置加密密钥，与应用程序中使用的密钥一致
        encryptor.setPassword("mysecretkey");
        
        // 待加密的敏感数据
        String originalText = "mySensitiveData";
        // 使用 encrypt() 方法对文本进行加密
        String encryptedText = encryptor.encrypt(originalText);
        System.out.println("加密后的文本: " + encryptedText);
        
        // 使用 decrypt() 方法解密文本
        String decryptedText = encryptor.decrypt(encryptedText);
        System.out.println("解密后的文本: " + decryptedText);
        
        // 确保解密后的文本与原始文本一致
        assertEquals(originalText, decryptedText);
    }
}

详细注释：
BasicTextEncryptor: Jasypt 提供的简易加密类，用于文本加密和解密。
encrypt(): 加密方法，将明文转换为加密后的文本。
decrypt(): 解密方法，将加密后的文本还原为明文。
assertEquals(): 测试框架中的断言方法，用于验证加密和解密是否正确。
测试输出：

加密后的文本: yxKTmDKoxkOTxF5GyqaDLw==
解密后的文本: mySensitiveData

测试通过则说明加密与解密功能正常。

五、总结
通过本文的详细步骤，我们了解了如何在 Spring Boot 项目中集成 Jasypt 进行加密和解密。Jasypt 可以通过简单的配置将敏感数据加密保存，从而提高应用程序的安全性。

集成步骤总结：
引入 Jasypt 依赖。
配置加密密钥。
对敏感数据进行加密，并在配置文件中使用 ENC() 包裹加密值。
Spring Boot 自动解密 ENC() 包裹的值。
可根据需求自定义加密器，选择适合的加密算法和配置。
通过这种方式，我们可以在不修改代码的情况下轻松保护应用程序的敏感信息。
