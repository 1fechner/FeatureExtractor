-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 30, 2016 at 12:03 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `MA_AK`
--

-- --------------------------------------------------------

--
-- Table structure for table `AK_ASTA`
--

CREATE TABLE IF NOT EXISTS `AK_ASTA` (
`ID` int(11) NOT NULL,
  `BelongsTo` int(11) NOT NULL,
  `Context` varchar(255) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `CapabilityType` int(11) NOT NULL DEFAULT '0',
  `Type` int(11) NOT NULL DEFAULT '0',
  `Attribute` int(11) NOT NULL DEFAULT '0',
  `Source` varchar(300) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `AK_ASTA`
--

INSERT INTO `AK_ASTA` (`ID`, `BelongsTo`, `Context`, `Description`, `CapabilityType`, `Type`, `Attribute`, `Source`) VALUES
(1, 28, 'Dataset is non model centric', 'Of course if your dataset is by nature non domain model centric, then Hibernate ORM is not for you. Relations and their concept of mapping does not work.', 7, 2, 0, 'http://hibernate.org/ogm/faq/'),
(3, 69, 'When using composite keys', 'This, in my opinion, is the biggest headache of the JPA developers. When we map a composite key we are adding a huge complexity to the project when we need to persist or find a object in the database. ', 7, 2, 0, 'https://dzone.com/articles/jpa-hibernate-alternatives'),
(4, 5, 'When using complex queries without indexing', 'That are projects that has several queries with a high level of complexity using database resources like: SUM, MAX, MIN, COUNT, HAVING, etc. If you combine those resources the JPA performance might drop and not use the table indexes, or you will not be ab', 7, 2, 0, 'https://dzone.com/articles/jpa-hibernate-alternatives'),
(5, 5, 'When using long running operations when transactions stay open for a long time', 'Slow processing and a lot of RAM memory occupied: There are moments that JPA will lose performance at report processing, inserting a lot of entities or problems with a transaction that is opened for a long time.', 7, 2, 0, 'https://dzone.com/articles/jpa-hibernate-alternatives'),
(6, 36, 'When loading large amounts of entites and performance is an issue', 'Enables lazy loading of unordered, unkeyed collections with duplicates', 7, 1, 0, 'http://forum.spring.io/forum/spring-projects/data/32636-how-persistent-bag-works-in-hibernate-world'),
(7, 36, 'When using Lists to represent bag semantics in general', 'As many developers do, the persistent bag follows the representation of bags as lists', 7, 1, 0, 'http://forum.spring.io/forum/spring-projects/data/32636-how-persistent-bag-works-in-hibernate-world'),
(8, 36, 'When using Persistent Bags instead of other lists', 'Creates additional dependencies to Hibernate datatypes', 7, 2, 0, ''),
(10, 36, 'A java.util.Collection can be mapped with <bag> or <idbag>', ' Java doesn’t have a Bag interface or an implementation; however, java.util. Collection allows bag semantics (possible duplicates, no element order is preserved). Hibernate supports persistent bags (it uses lists internally but ignores the index of the el', 7, 2, 0, 'http://what-when-how.com/hibernate/mapping-collections-and-entity-associations-hibernate/'),
(11, 36, 'When a collection does not need to be ordered', 'Bags provide better performance than sets', 7, 1, 0, 'https://vladmihalcea.com/2013/10/16/hibernate-facts-favoring-sets-vs-bags/'),
(12, 97, 'When the collections structure is modified', 'Unidirectional bags are not as efficient when it comes to modifying the collection structure (removing or reshuffling elements). Because the parent-side cannot uniquely identify each individual child, Hibernate might delete all child table rows associate ', 7, 2, 0, ''),
(13, 113, 'When a collection does not need to be ordered', 'Good performance overall', 0, 2, 0, ''),
(14, 107, 'When data is loaded often', 'Due to a missing optimization, response times will be high.This could cause high response times.', 0, 2, 0, 'https://community.oracle.com/thread/876139?tstart=0'),
(16, 110, 'When data is loaded often', 'Due to a specific optimization, batch fetching will remain delivering fast responses for large bulks, even when accessed by multiple processes.Queries are combined into a single one instead of accessing multiple queries', 0, 1, 0, 'https://community.oracle.com/thread/876139?tstart=0'),
(18, 148, 'awdwad', '', 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `AK_Feature`
--

CREATE TABLE IF NOT EXISTS `AK_Feature` (
`ID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Association` varchar(255) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=80 ;

--
-- Dumping data for table `AK_Feature`
--

INSERT INTO `AK_Feature` (`ID`, `Name`, `Description`, `Association`) VALUES
(1, 'Inheritance (ORM)', 'Be able to use inheritance, create hierarchies between entities', 'ORM'),
(2, 'Polymorphism', 'Polymorphism for entities. Used for ORM', 'ORM'),
(3, 'Relations (1-1)', 'Handle 1-1 relations when using entities', 'ORM'),
(4, 'Relations (1-n)', 'Handle 1-n Relations when using entities', 'ORM'),
(5, 'Relations (m-n)', 'Handle 1-n Relations when using entities', 'ORM'),
(7, 'Support for MySQL Databases', 'Support for MySQL Databases', 'Database Support'),
(8, 'Support for Oracle 11g Databases', 'Support for Oracle 11g Databases', 'Database Support'),
(9, 'Support for Microsoft SQL Databases', 'Support for Microsoft SQL Databases', 'Database Support'),
(10, 'Support for Sybase Databases', 'Support for Sybase Databases', 'Database Support'),
(11, 'Support for PostgreSQL Databases', 'Support for PostgreSQL Databases', 'Database Support'),
(12, 'Support for H2 Databases', 'Support for H2 Databases', 'Database Support'),
(13, 'Support for Derby Databases', 'Support for Derby Databases', 'Database Support'),
(14, 'Support for Firebird Databases', 'Support for Firebird Databases', 'Database Support'),
(15, 'Support for DB2 Databases', 'Support for DB2 Databases', 'Database Support'),
(16, 'Support for SQLiteJDBC Databases', 'Support for SQLiteJDBC Databases', 'Database Support'),
(17, 'Support for Oracle 11g RAC Databases', 'Support for Oracle 11g RAC Databases', 'Database Support'),
(18, 'Support for HSQLDB Databases', 'Support for HSQLDB Databases', 'Database Support'),
(19, 'Loading (Lazy)', 'Enables lazy loading of entities at runtime', 'ORM'),
(21, 'Transactions', 'Support for Database transactions', 'ORM'),
(22, 'Stored Procedures', 'Calling procedures stored in the database. Usually done using raw sql', 'ORM'),
(23, 'Sessions', 'Performing work as part of a session, useful when modifying large amounts of data', 'ORM'),
(24, 'Loading (Eager)', 'Enables eager loading of data at runtime', 'ORM'),
(25, 'Binary Large Objects (Blob)', 'Usage of Binary Large Objects', 'ORM'),
(26, 'Relations', 'Relations between elements of a database (E.g. between two tables)', 'ORM'),
(27, 'Database Vendor Support', 'Support for connections to various different databases, usually by using a vendor-specific driver', 'Database Support'),
(28, 'Dependency Injection', 'Injection of dependencies at runtime.', 'Runtime Configuration'),
(29, 'Mapping database tables/relations to POJOs', 'Creating and saving POJOs from a database. Core of the ORM mechanism', 'ORM'),
(30, 'Unordered, unkeyed collection for ORM result sets', 'An unordered, unkeyed collection for results of ORM mapped queries', 'ORM'),
(31, 'Loading of entities', 'Loading of database entities at runtime', 'ORM'),
(32, 'Single Table Inheritance', 'One table for everything', 'ORM'),
(33, 'Table per Class Inheritance', 'Mapping one table per class', 'ORM'),
(34, 'Joined table Inheritance', 'Mapping joined tables per class', 'ORM'),
(35, 'Mapping Enums', 'Mapping database values to enums', 'ORM'),
(36, 'Unidirectional Bags', 'Collections of mapped entities, unidirectional', 'ORM'),
(37, 'Bidirectional Bags', 'Collections of mapped entities, bidirectional', 'ORM'),
(38, 'Prepared Statements', 'Usage of prepared statements', 'SQL'),
(39, 'Direct database connection', 'Establishing a direct connection to a database', 'SQL'),
(40, 'Deployment', 'Applications can be compiled and packed into various formats to be deployed and conveniently executed at runtime', 'Runtime Configuration'),
(41, 'Collection Fetching (Immediate)', 'Fetch collections immediatly', 'ORM'),
(42, 'Collection Fetching (Lazy)', 'Fetch collections lazy', 'ORM'),
(43, 'Collection Fetching (Extra Lazy)', 'Fetch collections extra lazy', 'ORM'),
(44, 'Collection Fetching', 'Fetching, essentially, is the process of grabbing data from the database and making it available to the application', 'ORM'),
(45, 'Relations Fetching', 'Fetching entities in relations', 'ORM'),
(46, 'Collections', 'Persisting collections', 'ORM'),
(47, 'Bags', 'Using bags as collections of ORM mappings', 'ORM'),
(48, 'Native SQL Queries', 'Executing native SQL queries', 'SQL'),
(49, 'Generation of Identifiers', 'Have the ORM generate Identifiers according to the database', 'ORM'),
(50, 'Cascading associations between entities', 'Cascading operation applied to a given entity in various ways', 'ORM'),
(51, 'Entity Locking', 'Locking entities when accessing such', 'ORM'),
(52, 'Support OSGi', 'Support for OSGi', 'OSGi'),
(53, 'Relations Fetching (Eager)', 'Fetch entities in relation anyway', 'ORM'),
(54, 'Relations Fetching (Lazy)', 'Fetch entities in relation when needed', 'ORM'),
(55, 'Specifying Columns for fields', 'Specifying Columns for fields when mapping database tables to POJOs', 'ORM'),
(56, 'JpaRepository', 'The JPA module of Spring Data contains a custom namespace that allows defining repository beans. Allows to integrate datasources into a Spring application', 'JPA'),
(57, 'Direct Query Binding', 'Direct binding of Queries for ORM selects', 'ORM'),
(58, 'Factory for direct DB connections', 'Provides a factory to create direct database connections', 'SQL'),
(59, 'Ordering result lists', 'Ordering results lists when fetching enties', 'ORM'),
(60, 'Cascade all operations on entities', 'Cascade all operations performed on mapped entities, e.g. detaching, deleting, etc.', 'ORM'),
(61, 'Cascade detach operations on entities', 'Cascade detach operations performed on mapped entities', 'ORM'),
(62, 'Cascade merge operations on entities', 'Cascade merge operations performed on mapped entities', 'ORM'),
(63, 'Cascade persist operations on entities', 'Cascade persist operations performed on mapped entities', 'ORM'),
(64, 'Cascade refresh operations on entities', 'Cascade refresh operations performed on mapped entities', 'ORM'),
(65, 'Cascade remove operations on entities', 'Cascade remove operations performed on mapped entities', 'ORM'),
(66, 'Entities (Explicit)', 'Entity mapping can be explicitly specified', 'ORM'),
(67, 'Specifying Primary Tables for Entities', 'Specifying the main table for an entity', 'ORM'),
(68, 'Transient properties or fields', 'Defining non-persistent properties or fields', 'ORM'),
(69, 'Specifying IDs for Entities', 'Define which field should serve as an ID', 'ORM'),
(70, 'Joining Tables for Entities', 'Joining multiple tables for entities', 'ORM'),
(71, 'Joining Columns for Entities', 'Joining Columns for Entities', 'ORM'),
(72, 'Mapping Enums by Name', 'Mapping Enums by their name', 'ORM'),
(73, 'Mapping Enums by Ordinal', 'Mapping Enums by their number', 'ORM'),
(74, 'Composite identifiers (non-aggregated)', 'Usage of Composite identifiers without aggregation', 'ORM'),
(75, 'Persistence Contexts', 'Provides a scope for persistence', 'ORM'),
(76, 'Batch Retrieving', 'Processing a batch of entities at once. When reading multiple entities, instead of a query for each, multiple entities are selected in a single query, hence improving performance', 'ORM'),
(77, 'Relations (n-1)', 'Handle n-1 relations when using entities', 'ORM'),
(78, 'Embeddable Classes in Entities', '', 'ORM'),
(79, 'Enforcing Indices (DB)', '', 'ORM');

-- --------------------------------------------------------

--
-- Table structure for table `AK_Indicator`
--

CREATE TABLE IF NOT EXISTS `AK_Indicator` (
`ID` int(11) NOT NULL,
  `BelongsTo` int(11) NOT NULL,
  `Type` int(11) NOT NULL DEFAULT '0',
  `IndicatorLanguage` int(11) NOT NULL DEFAULT '0',
  `Confidence` int(11) NOT NULL DEFAULT '0',
  `Value` varchar(255) NOT NULL,
  `Parameter` varchar(255) NOT NULL,
  `Scope` varchar(255) NOT NULL,
  `Note` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=117 ;

--
-- Dumping data for table `AK_Indicator`
--

INSERT INTO `AK_Indicator` (`ID`, `BelongsTo`, `Type`, `IndicatorLanguage`, `Confidence`, `Value`, `Parameter`, `Scope`, `Note`) VALUES
(1, 3, 11, 2, 4, 'one-to-many', '', '', ''),
(2, 3, 11, 2, 4, 'hibernate-mapping', '', '', 'Identify Hibernate Mapping'),
(3, 4, 11, 2, 4, 'many-to-one', 'unique = "true"', '', ''),
(4, 6, 10, 1, 4, 'OneToOneCascade', '', 'org.sormula.annotation.cascade', 'Annotation is implicit, even if not specified'),
(5, 6, 4, 1, 4, 'org.sormula.annotation.cascade', '', 'class', 'Inclusion is optional, especially when annotation is not used'),
(6, 4, 11, 2, 4, 'hibernate-mapping', '', '', 'Identify Hibernate Mapping'),
(7, 7, 11, 2, 4, 'many-to-many', '', '', ''),
(8, 7, 11, 2, 4, 'hibernate-mapping', '', '', 'Identify Hibernate Mapping'),
(9, 9, 10, 1, 4, 'OneToManyCascade', '', '', ''),
(10, 10, 2, 0, 4, '"jdbc:mysql://', '', 'class', ''),
(11, 11, 2, 0, 4, 'org.hibernate.dialect.MySQLDialect', '', '*', ''),
(12, 11, 2, 0, 4, 'com.mysql.jdbc.Driver', '', '*', ''),
(14, 23, 2, 0, 4, '"jdbc:oracle:thin:', '', '', ''),
(15, 32, 10, 1, 4, 'Autowired', '', 'org.springframework.beans.factory.annotation', ''),
(16, 32, 4, 1, 4, 'org.springframework.beans.factory.annotation.Autowired', '', 'class', ''),
(17, 33, 4, 1, 4, 'org.hibernate.Transaction', '', 'class', ''),
(18, 33, 8, 1, 4, 'beginTransaction', '', 'class', ''),
(19, 36, 4, 1, 4, 'org.hibernate.collection.internal.PersistentBag', '', 'class', ''),
(20, 36, 7, 1, 4, 'PersistentBag', '', 'class', ''),
(21, 37, 7, 1, 4, 'Session', '', 'class', ''),
(22, 37, 4, 1, 4, 'org.hibernate.Session', '', 'class', ''),
(23, 38, 4, 1, 4, 'org.hibernate.SessionFactory', '', 'class', ''),
(24, 38, 7, 1, 4, 'SessionFactory', '', 'class', ''),
(25, 39, 4, 1, 4, 'org.hibernate.jdbc.Work', '', '', ''),
(26, 39, 7, 1, 4, 'Work', '', 'org.hibernate.jdbc', ''),
(27, 40, 10, 1, 4, 'Proxy', 'lazy = false', 'org.hibernate.annotations', ''),
(28, 41, 10, 1, 4, 'LazyCollection', 'LazyCollectionOption.FALSE', 'org.hibernate.annotations', 'https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/performance.html#performance-fetching-initialization'),
(29, 42, 10, 1, 4, 'LazyCollection', 'LazyCollectionOption.TRUE', 'org.hibernate.annotations', 'https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/performance.html#performance-fetching-initialization'),
(30, 43, 10, 1, 4, 'LazyCollection', 'LazyCollectionOption.EXTRA', 'org.hibernate.annotations', 'https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/performance.html#performance-fetching-initialization'),
(31, 44, 10, 1, 4, 'Cascade', 'CascadeType.LOCK', 'org.hibernate.annotations', 'https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/performance.html#performance-fetching-initialization'),
(32, 47, 5, 1, 4, 'JpaRepository', '', 'org.springframework.data.jpa.repository', ''),
(33, 47, 4, 1, 4, 'org.springframework.data.jpa.repository', '', 'class', ''),
(34, 48, 10, 1, 4, 'Query', '', 'org.springframework.data.jpa.repository', ''),
(35, 48, 4, 1, 4, 'org.springframework.data.jpa.repository.Query', '', 'class', ''),
(36, 49, 10, 1, 4, 'Query', 'value = ', 'org.springframework.data.jpa.repository', ''),
(37, 49, 4, 1, 4, 'org.springframework.data.repository.query.Param', '', 'class', ''),
(38, 49, 10, 1, 4, 'Param', '".*"', 'org.springframework.data.repository.query', ''),
(39, 50, 10, 1, 4, 'Table', '', 'javax.persistence', ''),
(40, 50, 4, 1, 4, 'javax.persistence.Table', '', 'class', ''),
(41, 51, 10, 1, 4, 'Entity', '', 'javax.persistence', ''),
(42, 51, 4, 1, 4, 'javax.persistence.Entity', '', 'class', ''),
(43, 52, 4, 1, 4, 'javax.persistence.EntityManagerFactory', '', 'class', ''),
(44, 52, 7, 1, 4, 'EntityManagerFactory', '', 'javax.persistence', ''),
(45, 58, 4, 1, 4, 'javax.persistence.Column', '', 'class', ''),
(46, 58, 10, 1, 4, 'Column', '', 'javax.persistence', ''),
(47, 61, 10, 1, 4, 'PersistenceContext', '', 'javax.persistence', ''),
(48, 61, 4, 1, 4, 'javax.persistence.PersistenceContext', '', 'class', ''),
(49, 63, 4, 1, 4, 'javax.persistence.FetchType', '', 'class', ''),
(50, 63, 10, 1, 4, '*', 'fetch = FetchType.EAGER', '*', ''),
(51, 64, 10, 1, 4, '*', 'fetch = FetchType.LAZY', '*', ''),
(52, 64, 4, 1, 4, 'javax.persistence.FetchType', '', 'class', ''),
(53, 65, 4, 1, 4, 'javax.persistence.EntityManager', '', 'class', ''),
(54, 65, 7, 1, 4, 'EntityManager', '', 'javax.persistence', ''),
(55, 66, 10, 1, 4, 'JoinColumn', '', 'javax.persistence', ''),
(56, 66, 4, 1, 4, 'javax.persistence.JoinColumn', '', 'class', ''),
(57, 67, 10, 1, 4, 'JoinTable', '', 'javax.persistence', ''),
(58, 67, 4, 1, 4, 'javax.persistence.JoinTable', '', 'class', ''),
(59, 68, 10, 1, 4, 'Id', '', 'javax.persistence', ''),
(60, 68, 4, 1, 4, 'javax.persistence.Id', '', 'class', ''),
(61, 69, 10, 1, 4, 'IdClass', '', 'javax.persistence', ''),
(62, 69, 4, 1, 4, 'javax.persistence.IdClass', '', 'class', ''),
(63, 73, 10, 1, 4, 'ManyToOne', '', 'javax.persistence', ''),
(64, 73, 4, 1, 4, 'javax.persistence.ManyToOne', '', 'class', ''),
(65, 74, 10, 1, 4, 'ManyToMany', '', 'javax.persistence', ''),
(66, 74, 4, 1, 4, 'javax.persistence.ManyToMany', '', 'class', ''),
(67, 75, 4, 1, 4, 'javax.persistence.GeneratedValue', '', 'java', ''),
(68, 75, 10, 1, 4, 'GeneratedValue', '', 'javax.persistence', ''),
(69, 77, 4, 1, 4, 'javax.persistence.CascadeType', '', 'java', ''),
(70, 77, 10, 1, 4, '*', 'CascadeType.ALL', '*', ''),
(71, 78, 4, 1, 4, 'javax.persistence.CascadeType', '', 'java', ''),
(72, 78, 10, 1, 4, '*', 'CascadeType.DETACH', '*', ''),
(73, 79, 10, 1, 4, '*', 'CascadeType.MERGE', '*', ''),
(74, 79, 4, 1, 4, 'javax.persistence.CascadeType', '', 'java', ''),
(75, 80, 4, 1, 4, 'javax.persistence.CascadeType', '', 'java', ''),
(76, 81, 4, 1, 4, 'javax.persistence.CascadeType', '', 'java', ''),
(77, 82, 4, 1, 4, 'javax.persistence.CascadeType', '', 'java', ''),
(78, 80, 10, 1, 4, '*', 'CascadeType.PERSIST', '*', ''),
(79, 81, 10, 1, 4, '*', 'CascadeType.REFRESH', '*', ''),
(80, 82, 10, 1, 4, '*', 'CascadeType.REMOVE', '*', ''),
(81, 84, 4, 1, 4, 'javax.persistence.OrderBy', '', 'java', ''),
(82, 84, 10, 1, 4, 'OrderBy', '', 'javax.persistence', ''),
(83, 85, 4, 1, 4, 'javax.persistence.Enumerated', '', 'java', ''),
(84, 85, 10, 1, 4, 'Enumerated', '', 'javax.persistence', ''),
(85, 86, 4, 1, 4, 'javax.persistence.EnumType', '', 'java', ''),
(86, 86, 10, 1, 4, '*', 'EnumType.STRING', '*', ''),
(87, 87, 4, 1, 4, 'javax.persistence.EnumType', '', 'java', ''),
(88, 87, 10, 1, 4, '*', 'EnumType.ORDINAL', '*', ''),
(89, 88, 10, 1, 4, 'Transient', '', 'javax.persistence', ''),
(90, 88, 4, 1, 4, 'javax.persistence.Transient', '', 'java', ''),
(91, 89, 4, 1, 4, 'javax.persistence.Inheritance', '', 'java', ''),
(92, 89, 10, 1, 4, 'Inheritance', '', 'javax.persistence', ''),
(93, 90, 4, 1, 4, 'javax.persistence.InheritanceType', '', 'java', ''),
(94, 91, 4, 1, 4, 'javax.persistence.InheritanceType', '', 'java', ''),
(95, 92, 4, 1, 4, 'javax.persistence.InheritanceType', '', 'java', ''),
(96, 90, 10, 1, 4, '*', 'InheritanceType.JOINED', '*', ''),
(97, 91, 10, 1, 4, '*', 'InheritanceType.SINGLE_TABLE', '*', ''),
(98, 92, 10, 1, 4, '*', 'InheritanceType.TABLE_PER_CLASS', '*', ''),
(99, 93, 4, 1, 4, 'javax.sql.DataSource', '', 'java', ''),
(100, 93, 7, 1, 4, 'DataSource', '', 'javax.sql', ''),
(101, 94, 4, 1, 4, 'java.sql.Connection', '', 'java', ''),
(102, 94, 7, 1, 4, 'Connection', '', 'java.sql', ''),
(103, 95, 4, 1, 4, 'java.sql.PreparedStatement', '', 'java', ''),
(104, 95, 7, 1, 4, 'PreparedStatement', '', 'java.sql', ''),
(105, 96, 4, 1, 4, 'java.sql.Blob', '', 'java', ''),
(106, 96, 7, 1, 4, 'Blob', '', 'java.sql', ''),
(107, 107, 10, 1, 4, 'BatchSize', '', 'org.hibernate.annotations', ''),
(108, 107, 4, 1, 4, 'org.hibernate.annotations.BatchSize', '', 'java', ''),
(109, 150, 10, 1, 4, 'Inject', '', '', ''),
(110, 15, 2, 0, 4, 'com.microsoft.sqlserver.jdbc.SQLServerDriver', '', '', ''),
(111, 152, 11, 2, 4, 'hibernate-mapping', '', '', ''),
(112, 152, 11, 2, 4, 'many-to-one', '', '', ''),
(113, 153, 10, 1, 4, 'Embeddable', '', 'javax.persistence', ''),
(114, 153, 4, 1, 4, 'javax.persistence.Embeddable', '', 'class', ''),
(115, 154, 4, 1, 4, 'org.hibernate.annotations.Index', '', 'class', ''),
(116, 154, 10, 1, 4, 'Index', '', 'org.hibernate.annotations', '');

-- --------------------------------------------------------

--
-- Table structure for table `AK_TechnologyFeature`
--

CREATE TABLE IF NOT EXISTS `AK_TechnologyFeature` (
`ID` int(11) NOT NULL,
  `BelongsTo` int(11) NOT NULL,
  `DependsOn` int(11) DEFAULT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Feature` int(11) DEFAULT NULL,
  `CapabilityType` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=155 ;

--
-- Dumping data for table `AK_TechnologyFeature`
--

INSERT INTO `AK_TechnologyFeature` (`ID`, `BelongsTo`, `DependsOn`, `Name`, `Description`, `Feature`, `CapabilityType`) VALUES
(3, 5, 27, '1-n Relations (Configuration)', 'Mapping one-to-many relationships (Hibernate)', 4, 1),
(4, 5, 27, '1-1 Relations (Configuration)', 'Mapping one-to-one relationships (Hibernate)', 3, 1),
(6, 13, 28, '1-1 Relations (Annotations)', 'Mapping one-to-one Relationships (Sormula)', 3, 1),
(7, 5, 27, 'm-n Relations (Configuration)', 'Mapping many-to-many relationships (Hibernate)', 5, 1),
(8, 13, 28, 'm-n Relations (Annotations)', 'Mapping many-to-many Relationships (Sormula)', 5, 1),
(9, 13, 28, '1-n Relations (Annotations)', 'Mapping one-to-many relationships', 4, 1),
(10, 13, 30, 'Support MySQL', 'Support connection to MySQL-Databases', 7, 4),
(11, 5, 29, 'Support MySQL', 'Support connection to MySQL-Databases', 7, 4),
(12, 5, 29, 'Support Oracle 11g', 'Supports connections to Oracle 11g Databases', 8, 4),
(13, 5, 29, 'Support Oracle 11g RAC', 'Supports connections to Oracle 11g RAC Databases', 17, 4),
(14, 5, 29, 'Support DB2', 'Supports connections to DB2 Databases', 15, 4),
(15, 5, 29, 'Support Microsoft SQL', 'Supports connections to Microsoft SQL Databases', 9, 4),
(16, 5, 29, 'Support Sybase', 'Supports connections to Sybase Databases', 10, 4),
(17, 5, 29, 'Support PostgreSQL', 'Supports connections to PostgreSQL Databases', 11, 4),
(18, 13, 30, 'Support H2', 'Supports connections to H2 Databases', 12, 4),
(19, 13, 30, 'Support HSQLDB', 'Supports connections to HSQLDB Databases', 18, 4),
(20, 13, 30, 'Support SQLiteJDBC', 'Supports connections to SQLiteJDBC Databases', 16, 4),
(21, 13, 30, 'Support Firebird', 'Supports connections to Firebird Databases', 14, 4),
(22, 13, 30, 'Support DB2', 'Supports connections to DB2 Databases', 15, 4),
(23, 13, 30, 'Support Oracle 11g', 'Supports connections to Oracle 11g Databases', 8, 4),
(24, 13, 30, 'Support PostgreSQL', 'Supports connections to PostgreSQL Databases', 11, 4),
(27, 5, NULL, 'Associations in Mapping-Configuration', 'Associations describe how two or more entities form a relationship based on a database joining semantics.', 26, 1),
(28, 13, NULL, 'Relations', 'Relations of various types (1-n, 1-2 etc.) can be ...', 26, 1),
(29, 5, NULL, 'Database Support', 'Supporting various kinds of databases', 27, 4),
(30, 13, NULL, 'Database Support', 'Supporting various kinds of databases', 27, 4),
(31, 5, 46, 'Loading (Lazy)', 'Lazy fetching decides whether to load child objects while loading the Parent Object. You need to do this setting respective hibernate mapping file of the parent class. Lazy = true (means not to load child) By default the lazy loading of the child objects ', 19, 1),
(32, 8, NULL, 'Autowiring', 'Autowiring dependencies', 28, 1),
(33, 5, NULL, 'Transactions', 'It is important to understand that the term transaction has many different yet related meanings in regards to persistence and Object/Relational Mapping. In most use-cases these definitions align, but that is not always the case.', 21, 1),
(34, 5, 56, 'Using stored procedures for querying', 'Hibernate provides support for queries via stored procedures and functions.', 22, 1),
(35, 15, NULL, 'Mapping entities', 'The Java Persistence API is a POJO persistence API for object/relational mapping. It contains a full object/relational mapping specification supporting the use of Java language metadata annotations and/or XML descriptors to define the mapping between Java', 29, 1),
(36, 5, 55, 'PersistentBag', 'An unordered, unkeyed collection that can contain the same element multiple times. The Java collections API, curiously, has no Bag. Most developers seem to use Lists to represent bag semantics, so Hibernate follows this practice.', 30, 1),
(37, 5, NULL, 'Session', 'A Session is used to get a physical connection with a database. The Session object is lightweight and designed to be instantiated each time an interaction is needed with the database. Persistent objects are saved and retrieved through a Session object.', 23, 1),
(38, 5, NULL, 'SessionFactory', 'The main contract here is the creation of Session instances. Usually an application has a single SessionFactory instance and threads servicing client requests obtain Session instances from this factory.', NULL, 1),
(39, 5, NULL, 'JDBC Work', 'Contract for performing a discrete piece of JDBC work.', NULL, 1),
(40, 5, 46, 'Loading (Eager)', 'will disable the default lazy loading for a particular entity. This means you always get the initialized entity whenever this entity is being referenced from other entities.', 24, 1),
(41, 5, 45, 'Collection Fetching (Immediate)', 'A collection is fetched before the application invokes an operation upon that collection. ', 41, 1),
(42, 5, 45, 'Collection Fetching (Lazy)', 'A collection is fetched after the application invokes an operation upon that collection. ', 42, 1),
(43, 5, 45, 'Collection Fetching (Extra Lazy)', 'A collection is fetched extra late after the application invokes an operation upon that collection. ', 43, 1),
(44, 5, NULL, 'Entity Locking', 'Locking of entities when accessing such. Used when cascading', 51, 1),
(45, 5, NULL, 'Collection Fetching', 'Fetching, essentially, is the process of grabbing data from the database and making it available to the application. Tuning how an application does fetching is one of the biggest factors in determining how an application will perform.', 44, 1),
(46, 5, NULL, 'Loading', 'Loading of Entities', 31, 1),
(47, 16, NULL, 'JpaRepository', 'The JPA module of Spring Data contains a custom namespace that allows defining repository beans. It also contains certain features and element attributes that are special to JPA. ', 56, 1),
(48, 16, 47, 'Direct Query Binding', 'Using named queries to declare queries for entities is a valid approach and works fine for a small number of queries. As the queries themselves are tied to the Java method that executes them you actually can bind them directly using the Spring Data JPA @Q', 57, 1),
(49, 16, 47, 'Direct Query Binding with Values', 'Using values to customize the query at runtime', NULL, 1),
(50, 15, 35, 'Specifying Primary Tables for Entities', 'Specifies the primary table for the annotated entity.', 67, 1),
(51, 15, 35, 'Entities', 'Explicitly defining POJOs as Entities', 66, 1),
(52, 15, NULL, 'EntityManagerFactory', 'Configuring the EntityManager', NULL, 1),
(54, 5, NULL, 'Collections', 'Naturally Hibernate also allows to persist collections. ', 46, 1),
(55, 5, 54, 'Bags', 'Bags are unordered lists and we can have unidirectional bags or bidirectional ones.', 47, 1),
(56, 5, NULL, 'Native SQL Queries', 'You may also express queries in the native SQL dialect of your database. This is useful if you want to utilize database specific features such as window functions, Common Table Expressions (CTE) or the CONNECT BY option in Oracle. It also provides a clean', 48, 1),
(57, 5, 56, 'Creating a native query using JPA', 'Execution of native SQL queries is controlled via the SQLQuery interface, which is obtained by calling Session.createSQLQuery(). The following sections describe how to use this API for querying.', 22, 1),
(58, 15, 35, 'Specifying Columns for fields', 'Specifies the column for fields of annotated entities', 55, 1),
(59, 5, NULL, 'OSGi', 'Hibernate targets the OSGi 4.3 spec or later. It was necessary to start with 4.3, over 4.2, due to our dependency on OSGi’s BundleWiring for entity/mapping scanning.', 52, 1),
(61, 15, NULL, 'Persistence Contexts', 'Both the org.hibernate.Session API and javax.persistence.EntityManager API represent a context for dealing with persistent data. This concept is called a persistence context.', 75, 1),
(62, 15, NULL, 'Relations Fetching', 'Specifies fetch types when entities are in a certain relationship', 45, 1),
(63, 15, 62, 'Eager Relations Fetching', 'Specifies fetch types when entities are in a certain relationship', 53, 1),
(64, 15, 62, 'Lazy Relations Fetching', 'Specifies fetch types when entities are in a certain relationship', 54, 1),
(65, 15, NULL, 'EntityManager', 'javax.persistence.EntityManager API represent a context for dealing with persistent data. This concept is called a persistence context. Persistent data has a state in relation to both a persistence context and the underlying database.', NULL, 1),
(66, 15, 35, 'Joining Columns for Entities', 'Specifies the column to join two tables by', 71, 1),
(67, 15, 35, 'Joining Tables for Entities', 'Specifies the parameters to join two tables by', 70, 1),
(68, 15, 35, 'Specifying IDs for Entities', 'Specifies the id for the annotated entity.', 69, 1),
(69, 15, 68, 'Composite identifiers (non-aggregated)', 'Modelling a composite identifier using an IdClass differs from using an EmbeddedId in that the entity defines each individual attribute making up the composition. The IdClass simply acts as a "shadow".', 74, 1),
(72, 15, NULL, 'Associations (JPA)', 'Associations describe how two or more entities form a relationship based on a database joining semantics.', 26, 1),
(73, 15, 72, 'n-1 Associations', '@ManyToOne is the most common association, having a direct equivalent in the relational database as well (e.g. foreign key), and so it establishes a relationship between a child entity and a parent.', 4, 1),
(74, 15, 72, 'm-n Associations', 'The @ManyToMany association requires a link table that joins two entities. ', 5, 1),
(75, 15, 68, 'Generation of values for simple identifiers', 'Values for simple identifiers can be generated. To denote that an identifier attribute is generated, it is annotated with javax.persistence.GeneratedValue', 49, 1),
(76, 15, 72, 'Cascading associations between entities', 'The “Cascade” keyword is often appear on the collection mapping to manage the state of the collection automatically. ', 50, 1),
(77, 15, 76, 'Cascade all operations', 'Cascade all operations', 60, 1),
(78, 15, 76, 'Cascade detach operation', 'Cascade detach operations', 61, 1),
(79, 15, 76, 'Cascade merge operation', 'Cascade merge operations', 62, 1),
(80, 15, 76, 'Cascade persist operation', 'Cascade persistence operations', 63, 1),
(81, 15, 76, 'Cascade refresh operation', 'Cascade refresh operations', 64, 1),
(82, 15, 76, 'Cascade remove operation', 'Cascade remove operations', 65, 1),
(84, 15, NULL, 'Ordering Lists', 'Specifies the ordering of the elements of a collection valued association or element collection at the point when the association or collection is retrieved.', 59, 0),
(85, 15, 35, 'Mapping Enums', 'Defines mapping for enumerated types. The constants of this enumerated type specify how a persistent property or field of an enumerated type should be persisted.', 35, 1),
(86, 15, 85, 'Mapping Enums by Name', 'Defines mapping for enumerated types. The constants of this enumerated type specify how a persistent property or field of an enumerated type should be persisted.', 72, 1),
(87, 15, 85, 'Mapping Enums by Ordinal', 'Defines mapping for enumerated types. The constants of this enumerated type specify how a persistent property or field of an enumerated type should be persisted.', 73, 1),
(88, 15, 35, 'Defining non-persistent properties or fields', 'Define properties that are not to be persistet', 68, 1),
(89, 15, 35, 'Mapping Inheritance', 'Mapping inheritance between POJOs from database tables', 1, 1),
(90, 15, 89, 'Mapping Joined Table Inheritance', NULL, 34, 1),
(91, 15, 89, 'Mapping Single Table Inheritance', NULL, 32, 1),
(92, 15, 89, 'Mapping Table per Class Inheritance', NULL, 33, 1),
(93, 17, NULL, 'DataSource', 'A factory for connections to the physical data source.   Most likely used to configure a database driver and its connection.', 58, 4),
(94, 17, NULL, 'Establishing direct Database Connection', 'A connection (session) with a specific database', 39, 1),
(95, 17, NULL, 'Prepared Statements', 'Sometimes it is more convenient to use a PreparedStatement object for sending SQL statements to the database. This special type of statement is derived from the more general class, Statement, that you already know. Tries to prevent things like SQL-Injecti', 38, 1),
(96, 17, NULL, 'Blobs', 'Usage of Binary Large Objects. E.g. for storing images in a database', 25, 1),
(97, 5, 55, 'Unidirectional Bags', 'The unidirectional bag is mapped using a single @OneToMany annotation on the parent side of the association. Behind the scenes, Hibernate requires an association table to manage the parent-child relationship', 36, 0),
(98, 5, 55, 'Bidirectional Bags', 'he bidirectional bag is the most common type of entity collection. The @ManyToOne side is the owning side of the bidirectional bag association, while the @OneToMany is the inverse side, being marked with the mappedBy attribute.', 37, 0),
(100, 27, 101, 'Support MySQL', 'Support connection to MySQL-Databases', 7, 0),
(101, 27, NULL, 'Database Support', 'Supporting various kinds of databases', 27, 4),
(102, 18, NULL, 'Database Support', 'Supporting various kinds of databases', 27, 4),
(103, 18, 102, 'Support MySQL', 'Support connection to MySQL-Databases', 7, 4),
(105, 18, NULL, 'Batch Reading', NULL, 76, 0),
(106, 18, NULL, 'Entity Locking', 'Oracle supports the locking policies shown in Table 9-4: no locking, optimistic, pessimistic, and read-only.', 51, 1),
(107, 5, NULL, 'Batch Reading', NULL, 76, 0),
(108, 18, NULL, 'Session', 'A Session is used to get a physical connection with a database. The Session object is lightweight and designed to be instantiated each time an interaction is needed with the database. Persistent objects are saved and retrieved through a Session object.', 23, 1),
(109, 18, NULL, 'Transactions', 'It is important to understand that the term transaction has many different yet related meanings in regards to persistence and Object/Relational Mapping. In most use-cases these definitions align, but that is not always the case.', 21, 1),
(110, 19, NULL, 'BatchFetch', NULL, 76, 0),
(112, 13, NULL, 'Transactions', 'It is important to understand that the term transaction has many different yet related meanings in regards to persistence and Object/Relational Mapping. In most use-cases these definitions align, but that is not always the case.', 21, 1),
(113, 27, 130, 'PersistentBag', 'An unordered, unkeyed collection that can contain the same element multiple times. The Java collections API, curiously, has no Bag. Most developers seem to use Lists to represent bag semantics, so Hibernate follows this practice.', 30, 0),
(114, 18, 46, 'Loading (Eager)', 'will disable the default lazy loading for a particular entity. This means you always get the initialized entity whenever this entity is being referenced from other entities.', 24, 1),
(115, 5, 45, 'Indirection', 'A collection is fetched after the application invokes an operation upon that collection. ', 42, 1),
(116, 19, 45, 'Indirection', 'A collection is fetched after the application invokes an operation upon that collection. ', 42, 1),
(117, 19, NULL, 'Entity Locking', 'Oracle supports the locking policies shown in Table 9-4: no locking, optimistic, pessimistic, and read-only.', 51, 1),
(118, 19, NULL, 'EclipseLink Sessions (ELUG)', 'A Session is used to get a physical connection with a database. The Session object is lightweight and designed to be instantiated each time an interaction is needed with the database. Persistent objects are saved and retrieved through a Session object.', 23, 1),
(119, 19, NULL, 'Database Support', 'Supporting various kinds of databases', 27, 4),
(120, 19, 119, 'Support MySQL', 'Support connection to MySQL-Databases', 7, 4),
(121, 19, NULL, 'EclipseLink Transactions (ELUG)', 'It is important to understand that the term transaction has many different yet related meanings in regards to persistence and Object/Relational Mapping. In most use-cases these definitions align, but that is not always the case.', 21, 1),
(122, 13, NULL, 'Defining non-persistent properties or fields', 'Define properties that are not to be persistet', 68, 1),
(123, 13, NULL, 'Specifying Columns for fields', 'Specifies the column for fields of annotated entities', 55, 1),
(124, 27, NULL, 'Transactions', 'It is important to understand that the term transaction has many different yet related meanings in regards to persistence and Object/Relational Mapping. In most use-cases these definitions align, but that is not always the case.', 21, 1),
(125, 27, NULL, 'Session', 'A Session is used to get a physical connection with a database. The Session object is lightweight and designed to be instantiated each time an interaction is needed with the database. Persistent objects are saved and retrieved through a Session object.', 23, 1),
(126, 13, NULL, 'Cascade all operations', 'Cascade all operations', 60, 1),
(127, 27, 130, 'Mapping Enums', 'Defines mapping for enumerated types. The constants of this enumerated type specify how a persistent property or field of an enumerated type should be persisted.', 35, 0),
(128, 27, NULL, 'Cascade all operations', 'Cascade all operations', 60, 1),
(129, 27, NULL, 'Composite identifiers (non-aggregated)', 'Modelling a composite identifier using an IdClass differs from using an EmbeddedId in that the entity defines each individual attribute making up the composition. The IdClass simply acts as a "shadow".', 74, 1),
(130, 27, 131, 'Mapping entities', 'The Java Persistence API is a POJO persistence API for object/relational mapping. It contains a full object/relational mapping specification supporting the use of Java language metadata annotations and/or XML descriptors to define the mapping between Java', 29, 0),
(131, 27, NULL, 'Entities', 'Explicitly defining POJOs as Entities', 66, 1),
(132, 27, 130, 'Defining non-persistent properties or fields', 'Define properties that are not to be persistet', 68, 0),
(133, 27, 130, 'Generation of values for simple identifiers', 'Values for simple identifiers can be generated. To denote that an identifier attribute is generated, it is annotated with javax.persistence.GeneratedValue', 49, 0),
(134, 27, 130, 'Mapping Inheritance', 'Mapping inheritance between POJOs from database tables', 1, 0),
(135, 27, NULL, 'Joining Columns for Entities', 'Specifies the column to join two tables by', 71, 1),
(136, 27, NULL, 'Entity Locking', 'Locking of entities when accessing such. Used when cascading', 51, 0),
(137, 27, 130, 'Specifying Primary Tables for Entities', 'Specifies the primary table for the annotated entity.', 67, 0),
(138, 27, 130, 'Specifying IDs for Entities', 'Specifies the id for the annotated entity.', 69, 0),
(139, 27, 130, 'Joining Tables for Entities', 'Specifies the parameters to join two tables by', 70, 0),
(140, 27, 130, 'Specifying Columns for fields', 'Specifies the column for fields of annotated entities', 55, 0),
(141, 27, NULL, 'Bulk Processing', NULL, 76, 0),
(142, 27, NULL, 'Ordering Lists', 'Specifies the ordering of the elements of a collection valued association or element collection at the point when the association or collection is retrieved.', 59, 0),
(143, 27, NULL, '1-n Relations (Configuration)', 'Mapping one-to-many relationships (Hibernate)', 4, 1),
(144, 27, NULL, 'm-n Relations (Configuration)', 'Mapping many-to-many relationships (Hibernate)', 5, 1),
(145, 27, NULL, 'Persistence Contexts', 'Both the org.hibernate.Session API and javax.persistence.EntityManager API represent a context for dealing with persistent data. This concept is called a persistence context.', 75, 1),
(146, 27, NULL, 'Loading (Eager)', 'will disable the default lazy loading for a particular entity. This means you always get the initialized entity whenever this entity is being referenced from other entities.', 24, 1),
(147, 27, 134, 'Mapping Joined Table Inheritance', '', 34, 0),
(148, 25, NULL, 'LINQ', NULL, NULL, 0),
(149, 32, 149, 'JSON support', 'QtCore now includes a set of classes for parsing and generating JSON documents. Those classes allow you to convert between in memory binary representation of JSON to standard textual JSON format. The goal is to make common operations on JSON fast.', NULL, 0),
(150, 34, NULL, 'Injection', 'Google Guice Dependency Injection using @Inject annotations', 28, 0),
(152, 5, 27, 'n-1 Relations (Configuration)', 'Implementation of n-1 relations', 77, 0),
(153, 15, 35, 'Embeddable Classes in Entities', 'Embeddable classes are used to represent the state of an entity but don’t have a persistent identity of their own, unlike entity classes. Instances of an embeddable class share the identity of the entity that owns it. ', 78, 0),
(154, 5, NULL, 'Enforcing Indices', '', 79, 0);

-- --------------------------------------------------------

--
-- Table structure for table `AK_TechnologySolution`
--

CREATE TABLE IF NOT EXISTS `AK_TechnologySolution` (
`ID` int(11) NOT NULL,
  `DependsOn` int(11) DEFAULT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `DecisionBuddyRef` int(11) DEFAULT NULL,
  `Display` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=37 ;

--
-- Dumping data for table `AK_TechnologySolution`
--

INSERT INTO `AK_TechnologySolution` (`ID`, `DependsOn`, `Name`, `Description`, `DecisionBuddyRef`, `Display`) VALUES
(2, 21, 'Java (Windows)', 'Implementation of the Java VM on Microsoft Windows', NULL, 1),
(3, 21, 'C++', 'C++ is a middle-level programming language developed by Bjarne Stroustrup starting in 1979 at Bell Labs. C++ runs on a variety of platforms, such as Windows, Mac OS, and the various versions of UNIX.', NULL, 1),
(4, 2, 'Spring', 'The Spring Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications - on any kind of deployment platform. A key element of Spring is infrastructural support at the application level: Spring focus', NULL, 1),
(5, 15, 'Hibernate', 'Working with both Object-Oriented software and Relational Databases can be cumbersome and time consuming. Development costs are significantly higher due to a paradigm mismatch between how data is represented in objects versus relational databases.', NULL, 1),
(6, 4, 'Spring Core Container', 'The Spring Core Container', NULL, 1),
(7, 6, 'Spring Core', 'The basic Spring Core components', NULL, 1),
(8, 6, 'Spring Beans', 'Spring''s take on dependency injection and Java EE', NULL, 1),
(10, 6, 'Spring SpEL', 'Spring Expression Language', NULL, 1),
(11, 4, 'Spring Data Access/Integration', 'Spring''s interface for various persistance technologies, such as Hibernate', NULL, 1),
(13, 2, 'Sormula', 'Sormula is a ORM OpenSource framework, very similar to the JPA/Hibernate.', NULL, 1),
(14, 2, 'sql2o', 'This framework works with native SQL and makes easier to transform database data into Java objects.', NULL, 1),
(15, 2, 'JPA', 'The Java Persistence API (JPA) is a Java application programming interface specification that describes the management of relational data in applications using Java Platform, Standard Edition and Java Platform, Enterprise Edition.', NULL, 1),
(16, 11, 'Spring ORM', 'Spring ORM', NULL, 1),
(17, 2, 'Java SQL', 'The standard Java interface for SQL connections', NULL, 1),
(18, 15, 'TopLink', 'Another JPA implementation', NULL, 1),
(19, 15, 'EclipseLink', 'Another JPA implementation', NULL, 1),
(21, NULL, 'Windows', 'The operating system Windows, by Microsoft', NULL, 1),
(22, NULL, 'Linux', 'Linux, a multi-user operation system invented by Linus Torvalds', NULL, 1),
(23, NULL, 'Mac OS', 'The operating system Mac OS', NULL, 1),
(24, 22, 'Java (Linux)', 'Implementation of the Java VM on Linux', NULL, 1),
(25, 21, '.NET', 'Microsoft''s .NET-Framework', NULL, 1),
(26, 25, 'C#', 'The Programming Language C#, based on Microsoft''s .NET-Framework', NULL, 1),
(27, 26, 'NHibernate', 'NHibernate is a mature, open source object-relational mapper for the .NET framework. It''s actively developed, fully featured and used in thousands of successful projects.', NULL, 1),
(28, 3, 'Qt', 'A modern user interface that is beautiful on every screen and performs perfectly on every platform is not an option, it''s a necessity. ', NULL, 1),
(29, 25, 'Visual Basic .NET', 'The Programming Language Visual Basic', NULL, 1),
(30, 26, 'Windows Forms', 'Windows Forms', NULL, 1),
(31, 26, 'WPF', 'Windows Presentation Foundation. UI-Plattform for the .NET-Framework', NULL, 1),
(32, 28, 'QtCore', 'The QtCore module contains core non-GUI functionality.', NULL, 1),
(33, 28, 'QtGui', 'Qt''s component for modern and fast UIs', NULL, 1),
(34, 2, 'Google Guice', 'Guice (pronounced ''juice'') is a lightweight dependency injection framework for Java 6 and above, brought to you by Google. https://github.com/google/guice', NULL, 1),
(35, 2, 'Google Web Toolkit', 'The Google WebToolkit (GWT)', NULL, 1),
(36, 2, 'Grails', 'Grails is a powerful web framework, for the Java platform aimed at multiplying developers’ productivity thanks to a Convention-over-Configuration, sensible defaults and opinionated APIs.', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ALT_Alternative`
--

CREATE TABLE IF NOT EXISTS `ALT_Alternative` (
`ID` int(11) NOT NULL,
  `Technology` int(11) NOT NULL,
  `IsAlternativeTo` int(11) NOT NULL,
  `Coverage` float NOT NULL,
  `Project` int(11) NOT NULL,
  `Version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ALT_DecisionPoint`
--

CREATE TABLE IF NOT EXISTS `ALT_DecisionPoint` (
`ID` int(11) NOT NULL,
  `CommonRoot` int(11) NOT NULL,
  `Alternative` int(11) NOT NULL,
  `DependencyChainLength` int(11) NOT NULL,
  `Project` int(11) NOT NULL,
  `Version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Result_Element`
--

CREATE TABLE IF NOT EXISTS `Result_Element` (
`ID` int(11) NOT NULL,
  `Project` int(11) NOT NULL,
  `Parent` int(11) DEFAULT NULL,
  `Name` varchar(255) NOT NULL,
  `Path` varchar(300) NOT NULL,
  `Package` varchar(255) NOT NULL,
  `FileType` varchar(255) NOT NULL,
  `Scanned` tinyint(1) NOT NULL DEFAULT '0',
  `Directory` tinyint(1) NOT NULL,
  `NoOfFeatures` int(11) NOT NULL DEFAULT '0',
  `Version` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Result_Project`
--

CREATE TABLE IF NOT EXISTS `Result_Project` (
`ID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Source` varchar(255) NOT NULL,
  `MinConfidence` float NOT NULL DEFAULT '1',
  `MinFeatureCoverage` float NOT NULL DEFAULT '90',
  `MaxDependencyChain` int(11) NOT NULL DEFAULT '100',
  `Version` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Result_Set`
--

CREATE TABLE IF NOT EXISTS `Result_Set` (
`ID` int(11) NOT NULL,
  `BelongsTo` int(11) DEFAULT NULL,
  `Project` int(11) NOT NULL,
  `TechnologyFeature` int(11) DEFAULT NULL,
  `Confidence` float NOT NULL DEFAULT '0',
  `Version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `AK_ASTA`
--
ALTER TABLE `AK_ASTA`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `AK_Feature`
--
ALTER TABLE `AK_Feature`
 ADD PRIMARY KEY (`ID`), ADD UNIQUE KEY `Name` (`Name`);

--
-- Indexes for table `AK_Indicator`
--
ALTER TABLE `AK_Indicator`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `AK_TechnologyFeature`
--
ALTER TABLE `AK_TechnologyFeature`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `AK_TechnologySolution`
--
ALTER TABLE `AK_TechnologySolution`
 ADD PRIMARY KEY (`ID`), ADD UNIQUE KEY `Name` (`Name`);

--
-- Indexes for table `ALT_Alternative`
--
ALTER TABLE `ALT_Alternative`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `ALT_DecisionPoint`
--
ALTER TABLE `ALT_DecisionPoint`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Result_Element`
--
ALTER TABLE `Result_Element`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Result_Project`
--
ALTER TABLE `Result_Project`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Result_Set`
--
ALTER TABLE `Result_Set`
 ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `AK_ASTA`
--
ALTER TABLE `AK_ASTA`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `AK_Feature`
--
ALTER TABLE `AK_Feature`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=80;
--
-- AUTO_INCREMENT for table `AK_Indicator`
--
ALTER TABLE `AK_Indicator`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=117;
--
-- AUTO_INCREMENT for table `AK_TechnologyFeature`
--
ALTER TABLE `AK_TechnologyFeature`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=155;
--
-- AUTO_INCREMENT for table `AK_TechnologySolution`
--
ALTER TABLE `AK_TechnologySolution`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `ALT_Alternative`
--
ALTER TABLE `ALT_Alternative`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ALT_DecisionPoint`
--
ALTER TABLE `ALT_DecisionPoint`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Result_Element`
--
ALTER TABLE `Result_Element`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Result_Project`
--
ALTER TABLE `Result_Project`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Result_Set`
--
ALTER TABLE `Result_Set`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
