
# TDD Using bottom-up approach

**This tutorial uses junit4**


|Test|Description|
|---|---|
|ReservationPojoTest||
|ReservationEntityTest|Persist into MongoDB tests|
|ReservationRepositoryTest|Testing Custom query aka Data tier|
|ReservationHTTPTest|Test HTTP API aka Web Tier|


|Class|Description|
|---|---|
|ReactiveRepository||
|ReactiveRepository extends ReactiveCrudRepository||

# Create your own matcher

**TOP: Don't use Matcher, use BaseMatcher. The documentation itself says so**

    new Matcher<String>() {
            @Override
            public boolean matches(Object o) {
                return false;
            }

            @Override
            public void describeMismatch(Object o, Description description) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
     }
     
     
# AssertJ

Junit and AssertJ both have Assertions. How an I use both in one test?


# ReservationEntityTest

## Mongo
    reactiveMongoTemplate.save()
    // Note that  if id is null, upon persisting Mongo creates random id value.
Mono == Publisher == ASYNC == Testing is difficult


## Note on ID

Use `@Id` [Spring Annotation] , to give another name to an identifier.

    public class Reservation {
    @Id
    private String myKey;
    }
If no `@Id`, Spring will give error on `.expectNextMatches(reservation2 -> reservation2.getMyKey() !=null)`    
If `@Id`, Spring will give error on `.expectNextMatches(reservation2 -> reservation2.getMyKey() ==null)` 
 


How to assert for reactive?  
Ans:

    StepVerifier
        .create(<publisher>)
        .expectNextMatches(<predicate>)
        .verifyComplete()
    
## `@Document`


# `ReservationRepositoryTest`
## `ReactiveCrudRepository` 
Spring Reactive CRUD Repository

## Test

    Flux<Reservation> reservationFlux = this.reservationRepository.deleteAll() //1.
                .thenMany(
                        Flux.just("A", "B", "C", "C") // 2.1. Create FLux
                        .map(name -> new Reservation(null, name)) // 2.2. Create Reservation objects for every Flux item
                        .flatMap(r -> this.reservationRepository.save(r)) // 2.3. Flatmap and save in database
                    )//2.
                .thenMany(this.reservationRepository.findByName("C"));//3. Query

        // Verifier results using StepVerifier

        StepVerifier
                .create(reservationFlux)
                .expectNextCount(2)
                .verifyComplete();
                
## `ReservationHTTPTest`

Web slice


# Consumer

## WebClientBuilder is bad but i used it here

## Use Wiremock to mock server for consumer



# Base class

## Prerequisite

Add maven plugin in producer's pom.xml
       
    <plugin>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-contract-maven-plugin</artifactId>
		<version>2.2.2.RELEASE</version>
		<extensions>true</extensions>
		<configuration>
			<baseClassForTests>
					com.ayman.contract.testing.BaseClass
			</baseClassForTests>
			<testMode>EXPLICIT</testMode>
		</configuration>
	</plugin>