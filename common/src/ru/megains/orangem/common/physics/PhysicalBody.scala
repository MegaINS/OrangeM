package ru.megains.orangem.common.physics



//
//class PhysicalBody(var minX:Float,var minY:Float,var minZ:Float,var maxX:Float,var maxY:Float,var maxZ:Float) {
//
//    def this(maxX:Float, maxY:Float, maxZ:Float){
//        this(0,0,0,maxX,maxY,maxZ)
//    }
//
//    def this(size:Float){
//        this(0,0,0,size,size,size)
//    }
//
//    def move(x: Float, y: Float, z: Float): Unit = {
//        minX += x
//        minY += y
//        minZ += z
//        maxX += x
//        maxY += y
//        maxZ += z
//    }
//    def rotate(side: Direction/*, boxes: BoundingBoxes*/): PhysicalBody  = {
//        side match {
//            case Direction.SOUTH=> new PhysicalBody( /*boxes.*/maxZ - maxZ , minY,minX , /*boxes.*/maxZ - minZ, maxY, maxX  )
//            case Direction.WEST=> new PhysicalBody(/*boxes.*/maxX - maxX, minY, /*boxes.*/maxZ - maxZ,/*boxes.*/maxX - minX, maxY,/*boxes.*/maxZ - minZ)
//            case Direction.NORTH=> new PhysicalBody(minZ, minY,/*boxes.*/maxX - maxX, maxZ , maxY, /*boxes.*/maxX - minX  )
//            case _  => getCopy
//        }
//    }
//    def sum(x: Float, y: Float, z: Float): PhysicalBody = new PhysicalBody(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z)
//
//    def set(minX: Float, minY: Float, minZ: Float, maxX: Float, maxY: Float, maxZ: Float): Unit = {
//        this.minX = minX
//        this.minY = minY
//        this.minZ = minZ
//        this.maxX = maxX
//        this.maxY = maxY
//        this.maxZ = maxZ
//    }
//
//
//    def getCenterX: Float = (maxX + minX) / 2
//
//    def getCenterZ: Float = (maxZ + minZ) / 2
//
//    def getCopy = new PhysicalBody(minX, minY, minZ, maxX, maxY, maxZ)
//
//    def set(aabb: PhysicalBody): Unit = {
//        this.minX = aabb.minX
//        this.minY = aabb.minY
//        this.minZ = aabb.minZ
//        this.maxX = aabb.maxX
//        this.maxY = aabb.maxY
//        this.maxZ = aabb.maxZ
//    }
//    /*
//        def expand(x: Float, y: Float, z: Float): AABB = {
//            val d0 = this.minX - x
//            val d1 = this.minY - y
//            val d2 = this.minZ - z
//            val d3 = this.maxX + x
//            val d4 = this.maxY + y
//            val d5 = this.maxZ + z
//            new AABB(d0, d1, d2, d3, d4, d5)
//        }
//    */
//    def expand(x: Float, y: Float, z: Float): PhysicalBody = {
//        var x0 = minX
//        var y0 = minY
//        var z0 = minZ
//        var x1 = maxX
//        var y1 = maxY
//        var z1 = maxZ
//        if (x > 0.0) x1 += x
//        else x0 += x
//        if (y > 0.0) y1 += y
//        else y0 += y
//        if (z > 0.0) z1 += z
//        else z0 += z
//        new PhysicalBody(x0, y0, z0, x1, y1, z1)
//    }
//
//    def checkXcollision(aabb: PhysicalBody, xIn: Float): Float = {
//        var x = xIn
//        if (minY < aabb.maxY && maxY > aabb.minY) if (minZ < aabb.maxZ && maxZ > aabb.minZ) {
//            var max = .0f
//            if (x > 0.0 && minX >= aabb.maxX - x) {
//                max = minX - aabb.maxX
//                if (max < x) x = max
//            }
//            if (x < 0.0 && maxX <= aabb.minX - x) {
//                max = maxX - aabb.minX
//                if (max > x) x = max
//            }
//        }
//        x
//    }
//
//    def checkYcollision(aabb: PhysicalBody, yIn: Float): Float = {
//        var y = yIn
//        if (minX < aabb.maxX && maxX > aabb.minX) if (minZ < aabb.maxZ && maxZ > aabb.minZ) {
//            var max = .0f
//            if (y > 0.0 && minY >= aabb.maxY) {
//                max = minY - aabb.maxY
//                if (max < y) y = max
//            }
//            if (y < 0.0 && maxY <= aabb.minY) {
//                max = maxY - aabb.minY
//                if (max > y) y = max
//            }
//        }
//        y
//    }
//
//    def checkZcollision(aabb: PhysicalBody, zIn: Float): Float = {
//        var z = zIn
//        if (minY < aabb.maxY && maxY > aabb.minY) if (minX < aabb.maxX && maxX > aabb.minX) {
//            var max = .0f
//            if (z > 0.0 && minZ >= aabb.maxZ - z) {
//                max = minZ - aabb.maxZ
//                if (max < z) z = max
//            }
//            if (z < 0.0 && maxZ <= aabb.minZ - z) {
//                max = maxZ - aabb.minZ
//                if (max > z) z = max
//            }
//        }
//        z
//    }
//
//    def calculateIntercept(vecA: Vector3d, vecB: Vector3d): (Vector3d,Direction) = {
//
//        var enumfacing:Direction = Direction.WEST
//
//        var vec3d = checkX(minX, vecA, vecB)
//        var vec3d1 = checkX(maxX, vecA, vecB)
//        if (vec3d1 != null && checkDistance(vecA, vec3d, vec3d1)) {
//            vec3d = vec3d1
//            enumfacing = Direction.EAST
//        }
//        vec3d1 = checkY(minY, vecA, vecB)
//        if (vec3d1 != null && checkDistance(vecA, vec3d, vec3d1)) {
//            vec3d = vec3d1
//            enumfacing = Direction.DOWN
//        }
//        vec3d1 = checkY(maxY, vecA, vecB)
//        if (vec3d1 != null && checkDistance(vecA, vec3d, vec3d1)) {
//            vec3d = vec3d1
//            enumfacing = Direction.UP
//        }
//        vec3d1 = checkZ(minZ, vecA, vecB)
//        if (vec3d1 != null && checkDistance(vecA, vec3d, vec3d1)) {
//            vec3d = vec3d1
//            enumfacing = Direction.NORTH
//        }
//        vec3d1 = checkZ(maxZ, vecA, vecB)
//        if (vec3d1 != null && checkDistance(vecA, vec3d, vec3d1)) {
//            vec3d = vec3d1
//            enumfacing = Direction.SOUTH
//        }
//        if (vec3d == null) null
//        else (vec3d, enumfacing)
//    }
//
//    def checkDistance(vec3f1:Vector3d, vec3f2: Vector3d, vec3f3: Vector3d): Boolean = {
//        vec3f2 == null || vec3f1.distanceSquared(vec3f3) < vec3f1.distanceSquared(vec3f2)
//    }
//
//    def checkX(x:Double, vec3f2: Vector3d, vec3f3: Vector3d): Vector3d = {
//        val vec3d = vec3f2.getIntermediateWithXValue(vec3f3, x)
//        if (vec3d != null && this.intersectsWithYZ(vec3d)) vec3d
//        else null
//    }
//
//    def checkY(y:Double, vec3f2: Vector3d, vec3f3: Vector3d): Vector3d = {
//        val vec3d = vec3f2.getIntermediateWithYValue(vec3f3, y)
//        if (vec3d != null && this.intersectsWithXZ(vec3d)) vec3d
//        else null
//    }
//
//    def checkZ(z:Double, vec3f2: Vector3d, vec3f3: Vector3d): Vector3d = {
//        val vec3d = vec3f2.getIntermediateWithZValue(vec3f3, z)
//        if (vec3d != null && this.intersectsWithXY(vec3d)) vec3d
//        else null
//    }
//
//    def intersectsWithYZ(vec: Vector3d): Boolean = vec.y >= this.minY && vec.y <= this.maxY && vec.z >= this.minZ && vec.z <= this.maxZ
//
//    def intersectsWithXZ(vec: Vector3d): Boolean = vec.x >= this.minX && vec.x <= this.maxX && vec.z >= this.minZ && vec.z <= this.maxZ
//
//    def intersectsWithXY(vec: Vector3d): Boolean = vec.x >= this.minX && vec.x <= this.maxX && vec.y >= this.minY && vec.y <= this.maxY
//}
//
//object PhysicalBody{
//    def apply(minX: Float, minY: Float,minZ: Float, maxX: Float, maxY: Float, maxZ: Float): PhysicalBody = new PhysicalBody(minX, minY, minZ, maxX, maxY, maxZ)
//
//
//}
